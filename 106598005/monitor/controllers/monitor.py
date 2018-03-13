import json
from time import gmtime, strftime
from flask import render_template
from run import get_blueprint

monitor = get_blueprint('monitor')


@monitor.route('/', methods=['GET', ])
def index():
    return render_template('index.html')


@monitor.route('/get_status_A', methods=['POST', ])
def get_status_A():

    json_content = {}
    json_content['last_check'] = strftime("%Y-%m-%d %H:%M:%S", gmtime())
    json_content['status'] = 'OK'
    return json.dumps(json_content, ensure_ascii=False)


@monitor.route('/get_status_B', methods=['POST', ])
def get_status_B():
    json_content = {}
    json_content['status'] = 'OK'
    return json.dumps(json_content, ensure_ascii=False)


@monitor.route('/get_status_C', methods=['POST', ])
def get_status_C():
    json_content = {}
    json_content['status'] = 'OK'
    return json.dumps(json_content, ensure_ascii=False)
