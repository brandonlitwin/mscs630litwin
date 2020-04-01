from datetime import datetime
from hashlib import md5
from flask_login import UserMixin
from werkzeug.security import generate_password_hash, check_password_hash
from app import db, login

class User(UserMixin, db.Model):
  id = db.Column(db.Integer, primary_key=True)
  username = db.Column(db.String(64), index=True, unique=True)
  email = db.Column(db.String(120), index=True, unique=True)
  password_hash = db.Column(db.String(128))
  about_me = db.Column(db.String(140))
  last_seen = db.Column(db.DateTime, default=datetime.utcnow)
  messages_sent = db.relationship('Message',
                                    foreign_keys='Message.sender_id',
                                    backref='sender', lazy='dynamic')
  messages_received = db.relationship('Message',
                                        foreign_keys='Message.recipient_id',
                                        backref='recipient', lazy='dynamic')
  last_message_read_time = db.Column(db.DateTime)

  def __repr__(self):
    return '<User {}>'.format(self.username) 

  def set_password(self, password):
    self.password_hash = generate_password_hash(password)

  def check_password(self, password):
    return check_password_hash(self.password_hash, password)

  def avatar(self, size):
    digest = md5(self.email.lower().encode('utf-8')).hexdigest()
    return 'https://www.gravatar.com/avatar/{}?d=identicon&s={}'.format(
            digest, size)

  def new_messages(self):
    last_read_time = self.last_message_read_time or datetime(1900, 1, 1)
    return Message.query.filter_by(recipient=self).filter(Message.timestamp > last_read_time).count()

class Message(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    sender_id = db.Column(db.Integer, db.ForeignKey('user.id'))
    recipient_id = db.Column(db.Integer, db.ForeignKey('user.id'))
    body = db.Column(db.String(140))
    timestamp = db.Column(db.DateTime, index=True, default=datetime.utcnow)

    def __repr__(self):
        return '<Message {}>'.format(self.body)

@login.user_loader
def load_user(id):
    return User.query.get(int(id))