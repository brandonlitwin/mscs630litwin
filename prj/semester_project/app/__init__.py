import os

from flask import Flask

app = Flask(__name__)

from app import routes

"""# a simple page that says hello
@app.route('/')
def hello():
    return 'Hello, World!'

if __name__ == "main":
    app.run(host='0.0.0.0', port=8080, debug=True)"""


"""def create_app(test_config=None):
    # create and configure the app
    app = Flask(__name__, instance_relative_config=True)
    app.config.from_mapping(
        SECRET_KEY='dev',
        DATABASE=os.path.join(app.instance_path, 'panpan.sqlite'),
    )

    if test_config is None:
        # load the instance config, if it exists, when not testing
        app.config.from_pyfile('config.py', silent=True)
    else:
        # load the test config if passed in
        app.config.from_mapping(test_config)

    # ensure the instance folder exists
    try:
        os.makedirs(app.instance_path)
    except OSError:
        pass

    

    return app"""