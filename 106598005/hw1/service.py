from flask import Flask, Blueprint

app = Flask(__name__)

blueprint = {'monitor': Blueprint('monitor', __name__)}


def get_blueprint(key):
    return blueprint[key]

from controllers import *
