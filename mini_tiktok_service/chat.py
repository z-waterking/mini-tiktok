#-*- coding:utf-8 -*-
# @Author:zsf
# @Time:2019/1/27 22:06
# @E-mail: 451540776@qq.com
import json, requests

def get_chat(content):
    api_url = "http://openapi.tuling123.com/openapi/api/v2"
    data = {
        "perception":
        {
            "inputText":
            {
                "text": content
            },
    # 可选参数
        },
        "userInfo":
        {
            "apiKey": "57d05afcdb8a45af87eccfad99c87546",
            "userId": "AA"
        }
    }
    data = json.dumps(data).encode('utf8')
    response_str = requests.post(api_url, data=data, headers={'content-type': 'application/json'})
    response_dic = response_str.json()
    results_text = response_dic['results'][0]['values']['text']
    return results_text
