from flask import Flask, request, jsonify

app = Flask(__name__)
from flask_cors import CORS

CORS(app, resources=r'/*')


@app.route('/')
def hello_world():
    return 'Hello, World!'


@app.route('/user/login', methods=['POST'])
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


@app.route('/user/register', methods=['POST'])
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


@app.route('/user/info', methods=['GET'])
def handler_get_user_info():
    # params = request.get_json(force=True)
    print(request.path, " : ",request.args)

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


@app.route('/user/update', methods=['POST'])
def deal_user_info_update():
    params = request.get_json(force=True)
    print(request.path, " : ", params)
    if 'phoneNum' in params.keys() and params['phoneNum'] == '18750946948':
        return jsonify({'status': {
            'code': 200,
            'msg': 'Update success'
        },
        })
    else:
        return jsonify({'status': {
            'code': 500,
            'msg': 'Update failed'
        }})


@app.route('/req/mine', methods=['GET'])
def deal_req_mine():
    # params = request.get_json(force=True)
    print(request.path, " : ",request.args)
    return jsonify({'status': {
        'code': 200,
        'msg': 'OK'
    },
        'issues': [
            {'id': 111111,
             'name': '海物1111',
             'username': 'haiwu1',
             'type': 0,
             'title': 'test1',
             'description': 'description1..................................',
             'headcount': 1,
             'createTime': 1636784940,
             'updateTime': 1636784940,
             'endTime': 1636784940,
             'commissionFee': 100.5,
             'state': 0},
            {'id': 222222,
             'name': '海物2',
             'username': 'haiwu2',
             'type': 1,
             'title': 'test2',
             'description': 'description2..................................',
             'headcount': 2,
             'createTime': 1636789940,
             'updateTime': 1636789940,
             'endTime': 1636789940,
             'commissionFee': 120.5,
             'state': 1},
            {'id': 33333,
             'name': '海物3',
             'username': 'haiwu3',
             'type': 2,
             'title': 'test3',
             'description': 'description3..................................',
             'headcount': 3,
             'createTime': 1636784940,
             'updateTime': 1636784940,
             'endTime': 1636784940,
             'commissionFee': 10.5,
             'state': 2},
            {'id': 44444,
             'name': '海物4',
             'username': 'haiwu4',
             'type': 3,
             'title': 'test4',
             'description': 'description4..................................',
             'headcount': 4,
             'createTime': 1636784940,
             'updateTime': 1636784940,
             'endTime': 1636784940,
             'commissionFee': 1000.5,
             'state': 3},
            {'id': 555555,
             'name': '海物5',
             'username': 'haiwu5',
             'type': 4,
             'title': 'test5',
             'description': 'description5..................................',
             'headcount': 1,
             'createTime': 1636784940,
             'updateTime': 1636784940,
             'endTime': 1636784940,
             'commissionFee': 100.5,
             'state': 0},
            {'id': 666666,
             'name': '海物1',
             'username': 'haiwu1',
             'type': 0,
             'title': 'test6',
             'description': 'description1..................................',
             'headcount': 1,
             'createTime': 1636784940,
             'updateTime': 1636784940,
             'endTime': 1636784940,
             'commissionFee': 100.5,
             'state': 0},
        ]
    })

@app.route('/req/delete',methods=['POST'])
def deal_req_delete():
    params = request.get_json(force=True)
    print(request.path, " : ", params)
    return jsonify({'status': {
        'code': 200,
        'msg': 'OK',
    }})

@app.route('/req/update',methods=['POST'])
def deal_req_update():
    params = request.get_json(force=True)
    print(request.path, " : ", params)
    return jsonify({'status': {
        'code': 200,
        'msg': 'OK',
    }})

@app.route('/req/add',methods=['POST'])
def deal_req_add():
    params = request.get_json(force=True)
    print(request.path, " : ", params)
    return jsonify({'status': {
        'code': 200,
        'msg': 'OK',
    },'id':123456})

@app.route('/req/query',methods=['GET'])
def deal_req_query():
    print(request.path, " : ",request.args)
    return jsonify({'status': {
        'code': 200,
        'msg': 'OK'
    },
        'issues': [
            {'id': 222222,
             'name': '海物2',
             'username': 'haiwu2',
             'type': 1,
             'title': 'test2',
             'description': 'description2..................................',
             'headcount': 2,
             'createTime': 1636789940,
             'updateTime': 1636789940,
             'endTime': 1636789940,
             'commissionFee': 120.5,
             'state': 1},
            {'id': 44444,
             'name': '海物4',
             'username': 'haiwu4',
             'type': 3,
             'title': 'test4',
             'description': 'description4..................................',
             'headcount': 4,
             'createTime': 1636784940,
             'updateTime': 1636784940,
             'endTime': 1636784940,
             'commissionFee': 1000.5,
             'state': 3},
            {'id': 666666,
             'name': '海物1',
             'username': 'haiwu1',
             'type': 0,
             'title': 'test6',
             'description': 'description1..................................',
             'headcount': 1,
             'createTime': 1636784940,
             'updateTime': 1636784940,
             'endTime': 1636784940,
             'commissionFee': 100.5,
             'state': 0},
        ]
    })

@app.route('/resp/accept',methods=['POST'])
def deal_resp_accept():
    params = request.get_json(force=True)
    print(request.path, " : ", params)
    return jsonify({'status': {
        'code': 200,
        'msg': 'OK',
    },'id':123456})


@app.route('/resp/mine', methods=['GET'])
def deal_resp_mine():
    print(request.path, " : ",request.args)
    return jsonify({'status': {
        'code': 200,
        'msg': 'OK'
    },
        'resps': [
            {'id': 1111110,
             'requestId':111111,
            'createTime':1637214055,
             'updateTime':1637214055,
             'description':'wowwwww',
             'state':0,
             },
            {'id': 1111120,
             'requestId': 111112,
             'createTime': 1637214055,
             'updateTime': 1637214055,
             'description': 'wowwwww',
             'state': 1,
             },
            {'id': 1111130,
             'requestId': 111113,
             'createTime': 1637214055,
             'updateTime': 1637214055,
             'description': 'wowwwww',
             'state': 2,
             },
            {'id': 1111140,
             'requestId': 111114,
             'createTime': 1637214055,
             'updateTime': 1637214055,
             'description': 'wowwwww',
             'state': 3,
             },
            {'id': 1111150,
             'requestId': 111115,
             'createTime': 1637214055,
             'updateTime': 1637214055,
             'description': 'wowwwww',
             'state': 0,
             },
        ]
    })
@app.route('/resp/delete',methods=['POST'])
def deal_resp_delete():
    params = request.get_json(force=True)
    print(request.path, " : ", params)
    return jsonify({'status': {
        'code': 200,
        'msg': 'OK',
    }})

@app.route('/resp/update',methods=['POST'])
def deal_resp_update():
    params = request.get_json(force=True)
    print(request.path, " : ", params)
    return jsonify({'status': {
        'code': 200,
        'msg': 'OK',
    }})

@app.route('/resp/query',methods=['GET'])
def deal_resp_query():
    print(request.path, " : ", request.args)
    return jsonify({'status': {
        'code': 200,
        'msg': 'OK',
    },'resps':[
        {
            'id':1024,
            'requestId':111111,
            'createTime':1637214055,
            'updateTime':1637214055,
            'description':'please~',
            'state':0,
            'respId':999,
            'respUsername':'haiwu',
            'respName':'海物',
        },
        {
            'id': 1025,
            'requestId': 222222,
            'createTime': 1637214055,
            'updateTime': 1637214055,
            'description': 'please~',
            'state':1,
            'respId': 999,
            'respUsername': 'haiwu',
            'respName': '海物',
        },
        {
            'id':1026,
            'requestId':333333,
            'createTime':1637214055,
            'updateTime':1637214055,
            'description':'please~',
            'state':2,
            'respId': 999,
            'respUsername': 'haiwu',
            'respName': '海物',
        },
        {
            'id': 1027,
            'requestId': 4444444,
            'createTime': 1637214055,
            'updateTime': 1637214055,
            'description': 'please~',
            'state': 3,
            'respId': 999,
            'respUsername': 'haiwu',
            'respName': '海物',
        }

    ]})

@app.route('/req/reply',methods=['POST'])
def deal_req_reply():
    params = request.get_json(force=True)
    print(request.path, " : ", params)
    return jsonify({'status': {
        'code': 200,
        'msg': 'OK',
    }})

@app.route('/census/query',methods=['GET'])
def deal_census_query():
    print(request.path, " : ", request.args)
    return jsonify({'status': {
        'code': 200,
        'msg': 'OK',
    },"datas":[{
        'year':2020,
        'month':1,
        'count':2,
        'totalFee':3.5,
    },{
        'year':2020,
        'month':2,
        'count':3,
        'totalFee':4.5,
    },{
        'year':2020,
        'month':3,
        'count':23,
        'totalFee':3.5,
    },{
        'year':2020,
        'month':4,
        'count':5,
        'totalFee':5.5,
    },{
        'year':2020,
        'month':5,
        'count':6,
        'totalFee':8.5,
    },{
        'year':2020,
        'month':6,
        'count':11,
        'totalFee':7.5,
    }]
    })
if __name__ == '__main__':
    app.run(port=5000)
                