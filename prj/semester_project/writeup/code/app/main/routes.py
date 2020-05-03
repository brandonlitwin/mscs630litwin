from datetime import datetime
from flask import render_template, flash, redirect, url_for, request
from werkzeug.urls import url_parse
from flask_login import current_user, login_user, logout_user, login_required
from app import db
from app.main.forms import EditProfileForm, MessageForm, HackerForm, DecryptForm
from app.models import User, Message, Hacker
from app.main import bp


@bp.before_request
def before_request():
    if current_user.is_authenticated:
        current_user.last_seen = datetime.utcnow()
        db.session.commit()

@bp.route('/', methods=['GET', 'POST'])
@bp.route('/index', methods=['GET', 'POST'])
@login_required
def index():
    victim = None
    victim = Hacker.query.filter_by(victim=current_user.id).first()
    if victim is not None and victim.successful:
        flash('You have been hacked! Go back to your profile and generate a new password ASAP!! Make sure you change the about me text to generate a different password')
    return render_template('index.html', title='Home', victim=victim)

@bp.route('/hall_of_shame')
@login_required
def hall_of_shame():
    hackers = User.query.order_by(User.successful_hacks.desc()).all()
    victims = User.query.order_by(User.times_hacked.desc()).all()
    return render_template('hall_of_shame.html', hackers=hackers, victims=victims)

@bp.route('/user/<username>', methods=['GET', 'POST'])
@login_required
def user(username):
    victim = None
    victim = Hacker.query.filter_by(victim=current_user.id).first()
    user = User.query.filter_by(username=username).first_or_404()
    if request.method=='POST':
        new_pass = _generate_encrypt_pass(user.about_me)
        if new_pass == "error":
            flash('You must have at least 3 words greater than 4 characters in your profile to generate a password.')
        else:  
            user.encrypt_password = new_pass
            if victim is not None:
                victim.change_hack_status(False)
            db.session.commit()
            flash("Your password is {}".format(new_pass))
    return render_template('user.html', user=user, victim=victim)

@bp.route('/edit_profile', methods=['GET', 'POST'])
@login_required
def edit_profile():
    form = EditProfileForm()
    if form.validate_on_submit():
        current_user.about_me = form.about_me.data
        db.session.commit()
        flash('Your changes have been saved.')
        return redirect(url_for('main.edit_profile'))
    elif request.method == 'GET':
        form.about_me.data = current_user.about_me
    return render_template('edit_profile.html', title='Edit Profile',
                           form=form)

@bp.route('/send_message/<recipient>', methods=['GET', 'POST'])
@login_required
def send_message(recipient):
    user = User.query.filter_by(username=recipient).first_or_404()
    form = MessageForm()
    if form.validate_on_submit():
        from Crypto.Cipher import AES
        from Crypto.Random import get_random_bytes
        from base64 import b64encode, b64decode
        key = get_random_bytes(16)
        AES_encryption_cipher = AES.new(key, AES.MODE_CFB)
        ciphertext = AES_encryption_cipher.encrypt(form.message.data.encode("utf-8"))
        iv = b64encode(AES_encryption_cipher.iv).decode("utf-8")
        ct = b64encode(ciphertext).decode('utf-8')
        iv = b64decode(iv)
        ct = b64decode(ct)
        AES_decryption_cipher = AES.new(key, AES.MODE_CFB, iv=iv)
        plaintext = AES_decryption_cipher.decrypt(ct)
        msg = Message(sender=current_user, recipient=user,
                      body=plaintext.decode("utf-8"), encrypted=b64encode(ct).decode("utf-8"), key=b64encode(key).decode("utf-8"))
        db.session.add(msg)
        db.session.commit()
        flash('Your message has been sent.')
        return redirect(url_for('main.user', username=recipient))
    return render_template('send_message.html', title='Send Message', form=form, recipient=recipient)

@bp.route('/messages', methods=['GET', 'POST'])
@login_required
def messages():
    current_user.last_message_read_time = datetime.utcnow()
    db.session.commit()
    page = request.args.get('page', 1, type=int)
    messages = current_user.messages_received.order_by(
        Message.timestamp.desc()).paginate(
            page, 10, False)
    next_url = url_for('main.messages', page=messages.next_num) \
        if messages.has_next else None
    prev_url = url_for('main.messages', page=messages.prev_num) \
        if messages.has_prev else None
    decrypt = False
    if request.method=='POST':
        decrypt = True
    return render_template('messages.html', messages=messages.items,
                           next_url=next_url, prev_url=prev_url, decrypt=decrypt)

@bp.route('/users', methods=['GET', 'POST'])
@login_required
def users():
    form = HackerForm()
    if form.validate_on_submit and request.method=='POST':
        choice = form.hacker_choice.data
        user = User.query.filter_by(username=choice).first()
        if user is None:
            flash('Please enter a valid username (check spelling)')
        else:
            hacker = Hacker(hacker=current_user.id, victim=user.id)
            db.session.add(hacker)
            db.session.commit()
            flash('You have chosen {}'.format(choice))
            
    page = request.args.get('page', 1, type=int)
    users = User.query.all()
    hacker = Hacker.query.filter_by(hacker=current_user.id).first()
    victim = None
    if hacker:
        victim = User.query.filter_by(id=hacker.victim).first()
    return render_template('users.html', users=users, form=form, victim=victim)

@bp.route('/hacked_messages', methods=['GET','POST'])
@login_required
def hacked_messages():
    form = DecryptForm()
    hacker = Hacker.query.filter_by(hacker=current_user.id).first()
    victim = None
    messages = None
    if hacker:
        victim = User.query.filter_by(id=hacker.victim).first()
    page = request.args.get('page', 1, type=int)
    if victim is not None:
        messages = victim.messages_received.order_by(Message.timestamp.desc()).paginate(
            page, 10, False)
        next_url = url_for('main.messages', page=messages.next_num) \
            if messages.has_next else None
        prev_url = url_for('main.messages', page=messages.prev_num) \
            if messages.has_prev else None
    decrypt = False
    if request.method=='POST':
        if form.decrypt_password.data == victim.encrypt_password:
            hacker.change_hack_status(True)
            hacker_user = User.query.filter_by(id=current_user.id).first()
            hacker_user.hack_successful()
            victim.got_hacked()
            db.session.commit()
            flash("Hacking successful!")
        else:
            flash("Hacking attempt failed")

    # query hacker again in case status changed
    #hacker = Hacker.query.filter_by(hacker=current_user.id).first()
    if messages is not None:      
        return render_template('hacked_messages.html', victim=victim, messages=messages.items, form=form, hacker=hacker)
    else:
        return render_template('hacked_messages.html', victim=victim, form=form, hacker=hacker) 

def _generate_encrypt_pass(about_me_string):
    import re, string, random
    new_pass = ""
    words = re.sub('['+string.punctuation+']', '', about_me_string).split()
    try:
        words_filtered = random.sample(list(filter(lambda x: len(x) > 4, words)), k=3)
        for word in words_filtered:
            new_pass += word.lower()
    except ValueError:
        new_pass = "error"
    if new_pass == "":
        new_pass = "error"
    return new_pass