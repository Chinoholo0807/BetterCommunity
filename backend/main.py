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
    if 'username' in params.keys() and params['username'] == 'admin':
        return jsonify({'status': {
            'code': 200,
            'msg': 'Login success'
        }, 'token': 'abcdefg'
        })
    else:
        return jsonify({'status': {
            'code': 400,
            'msg': '用户名或密码错误'
        }})


@app.route('/register', methods=['POST'])
def deal_register():
    params = request.get_json(force=True)
    print(request.path, " : ", params)
    if 'username' in params.keys() and params['username'] == 'admin':
        return jsonify({'status': {
            'code': 200,
            'msg': 'Register success'
        }, 'token': 'abcdefg'
        })
    else:
        return jsonify({'status': {
            'code': 500,
            'msg': '注册错误'
        }})


@app.route('/user/info', methods=['POST','GET'])
def handler_get_user_info():
    # params = request.get_json(force=True)
    print(request.path, " : ")
    return jsonify({'status': {
        'code': 200,
        'msg': 'Get info success'
    }, 'username': 'haiwu',
        'name': '海物',
        'type': 0,
        'certificateType': 0,
        'certificateNum': '350425200008072013',
        'phoneNum': '18750946948',
        'userLevel': 0,
        'introduction': '这是我的简介',
        'regCity': '北京市直辖市',
        'regCommunity': '金谷园社区',
        'regTime': 1636784940,
        'updateTime': 1636784940
    })


if __name__ == '__main__':
    app.run(port=5000)
