#-*- coding:utf-8 -*-
# @Author:zsf
# @Time:2019/1/29 0:36
# @E-mail: 451540776@qq.com

# -*- coding: UTF-8 -*-
import numpy as np
import matplotlib as mpl
import matplotlib.pyplot as plt
import random
import os

def get_line(datas, user_id):
    #datas按照时间逆序排序，即最近的在前面
    if os.path.exists("static/img/" + user_id + "/" + "line.png"):
        return "static/img/" + user_id + "/" + "line.png"
    num = len(datas) > 7 and 7 or len(datas)
    x = list(range(1, num+1))
    #分三条线
    scene = [0] * 7
    person = [0] * 7
    animals = [0] * 7
    #从数据开始进行筛选
    for i in range(num):
        if datas[i][0] == '场景':
            scene[i] = random.random() * 5
        elif datas[i][0] == '人物':
            person[i] = random.random() * 5
        else:
            animals[i] = random.random() * 5
    plt.title('Result Analysis')
    plt.plot(x, scene, color='green', label='scene')
    plt.plot(x, person, color='blue', label='person')
    plt.plot(x, animals, color='red', label='animals')
    plt.set_size_inches(3, 2)
    plt.legend()
    # 显示图例
    plt.xlabel('latest videl')
    plt.ylabel('rate')
    plt.savefig("static/img/" + user_id + "/" + "line.png")
    return "static/img/" + user_id + "/" + "line.png"