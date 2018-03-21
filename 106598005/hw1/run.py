from service import app, get_blueprint

app.register_blueprint(get_blueprint('monitor'), url_prefix='/monitor')
if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True, threaded=True)
