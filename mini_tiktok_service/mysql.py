import pymysql
import all_datas
import word_cloud
import datetime
import time
import random
import line
contents = ["场景", "人物", "动物"]
db = pymysql.Connect(host='10.1.0.196', port=3306, user='root', passwd='412214', db='android_douyin',
                         charset='utf8')
# except Exception as e:
#     print("connection failed")

#根据条件查找
def Search_By_Condition():
    sql = "select * from all_feeds;"
    #执行语句
    cur = db.cursor()
    cur.execute(sql)
    results = cur.fetchall()
    print(1)
    return results

#根据视频推荐
def get_recommend_by_video_url(video_url):
    '''

    :param video_url: 传入的视频url
    :return: {"success":True, "feeds":[] len=5}
    '''
    return_data = {"success":True, "recommend_feeds":[]}
    #先查看其content是否存在，不在的话赋值一个最多的content
    sql = "select content from all_feeds where video_url = '%s';" % video_url
    cur = db.cursor()
    cur.execute(sql)
    results = cur.fetchall()
    content = "场景"
    #如果存在，则将其content赋值
    if len(results) != 0:
        content = results[0][0]
    #推荐时先选出不是content的数据
    #共推荐五条
    all_nums = 5
    sql = "select user_name, student_id, image_url, video_url, content from all_feeds where content != '%s';" % content
    cur.execute(sql)
    results = cur.fetchall()
    now_num = len(results) > all_nums and all_nums or len(results)
    for d in range(now_num):
        temp = {}
        temp["user_name"] = results[d][0]
        temp["student_id"] = results[d][1]
        temp["image_url"] = results[d][2]
        temp["video_url"] = results[d][3]
        temp["content"] = results[d][4]
        return_data["recommend_feeds"].append(temp)
    return_data["recommend_feeds"].extend(all_datas.all_default_recommend_by_video_url["recommend_feeds"])
    return_data["recommend_feeds"] = return_data["recommend_feeds"][:5]
    return return_data

#根据人物推荐
def get_recommend_by_user_id(user_id):
    '''
    :param user_id:
    :return:
    '''
    #查看这个人最后观看的视频内容
    return_data = {"success": True, "recommend_feeds": []}
    # 先查看其content是否存在，不在的话赋值一个最多的content
    sql = "select content from sequence where user_id = '%s' order by time desc;" % user_id
    cur = db.cursor()
    cur.execute(sql)
    results = cur.fetchall()
    content = "场景"
    # 如果存在，则将其content赋值
    if len(results) != 0:
        content = results[0][0]
    # 推荐时先选出不是content的数据
    # 共推荐十条
    all_nums = 10
    sql = "select user_name, student_id, image_url, video_url, content from all_feeds where content != '%s';" % content
    cur.execute(sql)
    results = cur.fetchall()
    now_num = len(results) > all_nums and all_nums or len(results)
    for d in range(now_num):
        temp = {}
        temp["user_name"] = results[d][0]
        temp["student_id"] = results[d][1]
        temp["image_url"] = results[d][2]
        temp["video_url"] = results[d][3]
        temp["content"] = results[d][4]
        return_data["recommend_feeds"].append(temp)
    return_data["recommend_feeds"].extend(all_datas.all_default_recommend_by_video_url["recommend_feeds"])
    if len(return_data["recommend_feeds"])>10:
        return_data["recommend_feeds"] = return_data["recommend_feeds"][0:10]
    return return_data

def Insert_Feeds_datas(data):
    #插入所有的feed数据
    for feed in data:
        sql = "insert into all_feeds (user_name, student_id, image_url, video_url, content) values "\
            +"('{user_name}','{student_id}','{image_url}', '{video_url}', '{content}')"
        sql = sql.format(**feed)
        cur = db.cursor()
        cur.execute(sql)
    db.commit()

def Insert_Sequence_data(data):
    #插入sequence数据
    try:
        sql = "insert into sequence (user_id, image_url, time, content) values "\
            +"('{user_id}','{video_url}', {time}, '{content}')"
        sql = sql.format(**data)
        cur = db.cursor()
        cur.execute(sql)
        db.commit()
        return True
    except:
        return False

def get_content_by_video_url(video_url):
    sql = "select content from all_feeds where video_url = '%s';" % video_url
    cur = db.cursor()
    cur.execute(sql)
    results = cur.fetchall()
    content = "场景"
    # 如果存在，则将其content赋值
    if len(results) != 0:
        content = results[0][0]
    return content

def get_me(user_id):
    my_host = "http://10.1.0.195:8080/"
    #设置返回格式
    return_result = {"word_cloud":"", "line":"" }
    #设置词云图
    return_result["word_cloud"] = my_host + word_cloud.get_word_cloud(user_id)
    #设置折线图
    sql = "select content from sequence where user_id = '%s' order by time desc;" % user_id
    cur = db.cursor()
    cur.execute(sql)
    results = cur.fetchall()
    #返回默认数据
    #取得所有的结果
    return_result["line"] = my_host + line.get_line(results, user_id)
    return return_result

print(get_me("865873038279224"))