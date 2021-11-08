from flask import Flask, request, jsonify

app = Flask(__name__)
from flask_cors import CORS

CORS(app, resources=r'/*')


@app.route('/')
def hello_world():
    return 'Hello, World!'


@app.route('/login', methods=['POST'])
def deal_login():
    params = request.get_json(force=True)
    print(request.path, " : ", params)
    if 'userName' in params.keys() and params['userName'] == 'admin':
        return jsonify({'status': {
            'statusCode': 200,
            'statusMsg': 'Login success'
        },'token':'abcdefg'
        })
    else:
        return jsonify({'status': {
            'statusCode': 400,
            'statusMsg': '用户名或密码错误'
        }})


if __name__ == '__main__':
    app.run(port=5000)
