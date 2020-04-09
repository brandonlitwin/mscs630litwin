from flask_wtf import FlaskForm
from wtforms import SubmitField, TextAreaField, StringField
from wtforms.validators import Length, DataRequired

class EditProfileForm(FlaskForm):
    about_me = TextAreaField('About me', validators=[Length(min=0, max=140)])
    submit = SubmitField('Submit')

class MessageForm(FlaskForm):
    message = TextAreaField('Message', validators=[DataRequired(), Length(min=0, max=140)])
    submit = SubmitField('Submit')

class HackerForm(FlaskForm):
    hacker_choice = TextAreaField('Enter username', validators=[DataRequired()])
    submit = SubmitField('Submit')

class DecryptForm(FlaskForm):
    decrypt_password = StringField('Enter password', validators=[DataRequired()])
    submit = SubmitField('Submit')
