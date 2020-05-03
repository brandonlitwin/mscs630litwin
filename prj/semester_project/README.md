# Project: Pass a Note, Peek a Note

The goal of this project is to demonstrate a system that uses a popular security algorithm. 
My project demonstrates how AES can be used to encrypt and decrypt messages between users.
A full writeup of the design can be found in the `writeup/doc` folder.

# Running the Code
To run the project locally, simply clone this repo. All the Python code is inside the `writeup/code` folder, so `cd` there before doing the next steps.  
1. `pip install requirements.txt`. You may want to do this inside a Python3 virtual env.  
1. `flask run` and see the project running on `localhost:5000`.

If the database is not running, try `flask db init`, `flask db migrate`, `flask db upgrade` in that order to initialize the database.
