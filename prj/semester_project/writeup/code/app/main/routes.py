from datetime import datetime
from flask import render_template, flash, redirect, url_for, request
from werkzeug.urls import url_parse
from flask_login import current_user, login_user, logout_user, login_required
from app import db
from app.main.forms import EditProfileForm, MessageForm, HackerForm
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
    return render_template('index.html', title='Home')

@bp.route('/user/<username>')
@login_required
def user(username):
    user = User.query.filter_by(username=username).first_or_404()
    return render_template('user.html', user=user)

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

@bp.route('/messages')
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
    return render_template('messages.html', messages=messages.items,
                           next_url=next_url, prev_url=prev_url)

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
    if hacker:
        victim = User.query.filter_by(id=hacker.victim).first()
    return render_template('users.html', users=users, form=form, victim=victim)

@bp.route('/hacked_messages')
@login_required
def hacked_messages():
    hacker = Hacker.query.filter_by(hacker=current_user.id).first()
    victim = None
    if hacker:
        victim = User.query.filter_by(id=hacker.victim).first()
    return render_template('hacked_messages.html', victim=victim) 