from flask import Flask, render_template, url_for, redirect
import json
from flask import request
import base64
from flask import request, make_response
import chat
import time
import datetime
import all_datas
import mysql

app = Flask(__name__)
contents = ["场景", "人物", "动物"]
@app.route('/my',methods=['POST','GET'])
def index():
    return json.dumps({"test":"success"})

#进入推荐页面或者详情页面时，进行推荐，分为不同的参数。
#第一种是根据个人推荐，第二种是根据拿到的video_url进行推荐。
@app.route('/get_recommend_by_user_id',methods=['POST','GET'])
def get_recommend_by_user_id():
    '''
    参数格式：{"user_id":"text"}
    :return:
    '''
    try:
        user_id = request.args["user_id"]
        data = mysql.get_recommend_by_user_id(user_id)
        # return json.dumps(data)
        return json.dumps(data)
    except:
        return json.dumps(all_datas.all_default_recommend_by_video_url)

@app.route('/get_recommend_by_video_url',methods=['POST','GET'])
def get_recommend_by_video_url():
    '''
    参数格式：{"video_url":"text"}
    :return:
    '''
    video_url = request.args["video_url"]
    data = mysql.get_recommend_by_video_url(video_url)
    try:
        video_url = request.args["video_url"]
        data = mysql.get_recommend_by_video_url(video_url)
        return json.dumps(data)
        # return json.dumps(all_datas.all_default_recommend_by_video_url)
    except Exception as e:
        print(e)
        return json.dumps(all_datas.all_default_recommend_by_video_url)

#进入详情页面时，根据此URL取得对应的标签
@app.route('/get_content',methods=['POST','GET'])
def get_content():
    try:
        video_url = request.args["video_url"]
        content = mysql.get_content_by_video_url(video_url)
        data = {"content" : content}
        return json.dumps(data)
    except:
        return json.dumps({"content":"场景"})


#提交视频的点击序列
@app.route('/submit_sequence', methods=['POST', 'GET'])
def submit_sequence():
    '''
    参数格式：{"user_id":"text", "video_url":"text"}
    :return: {"success":"True"}
    '''
    user_id = request.args["user_id"]
    video_url = request.args["video_url"]
    content = mysql.get_content_by_video_url(video_url)

    t = int(time.time())

    data = {"user_id":user_id, "video_url":video_url, "time":t, "content":content}
    res_data = mysql.Insert_Sequence_data(data)
    if(res_data == True):
        return json.dumps({"success":True})
    else:
        return json.dumps({"success":False})

#自动回复
@app.route('/get_reply', methods=['POST', 'GET'])
def get_reply():
    '''
    参数格式：{"content":"text"}
    :return: {"content":"text"}
    '''
    content = request.args["content"]
    reply = chat.get_chat(content)
    print(reply)
    return json.dumps({"content":reply})

#取得自己的用户画像
@app.route('/get_me', methods=['POST', 'GET'])
def get_me():
    '''
    参数格式：{"user_id":"text"}
    :return: {"word_cloud_url":"url", "line":"url"}
    '''
    try:
        user_id = request.args["user_id"]
        return json.dumps(mysql.get_me(user_id))
    except:
        return {"word_cloud":"", "line":""}


if __name__ == '__main__':
    app.run('10.1.0.195', debug = True, port = 8080)

