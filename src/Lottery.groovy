/**
 * Created by Zerounary on 2019/6/2.
 */

@GrabConfig(systemClassLoader=true)
@Grapes(
        [
                @Grab(group='mysql', module='mysql-connector-java', version='5.1.39'),
                @Grab(group='org.jsoup', module='jsoup', version='1.9.1'),
                @Grab(group='com.alibaba', module='fastjson', version='1.2.58')
        ]
)
import groovy.sql.Sql
import org.jsoup.Jsoup
import com.alibaba.fastjson.JSONObject

def url = 'jdbc:mysql://192.168.1.6:3306/lottery'
def user = 'root'
def password = 'toor'
def driver = 'com.mysql.jdbc.Driver'
def sql = Sql.newInstance(url, user, password, driver)
def dataNum = 200
def listUrl = "https://m.cpzx18.com/v1/lottery/openResult?lotteryCode=1412&dataNum=${dataNum}&"
def doc, rst;
while (true){
    doc = Jsoup.connect(listUrl).ignoreContentType(true).get();
    data = JSONObject.parseObject(doc.text()).getJSONArray('data')
    println "获取${dataNum}条数据"
    for (it in data){
        if (sql.firstRow("SELECT COUNT(*) AS num FROM lotteryhis where issue =${it.issue}").num == 0){
            sql.executeInsert"""
              INSERT INTO lotteryhis(issue, lotteryCode, createdTime, open, openNumber, openTime)
              VALUES (${it.issue},${it.lotteryCode},${it.createdTime},${it.open},${it.openNumber},${it.openTime})
            """;
        }else{
            println "${it.issue}期的数据已存在"
        }
    }
    println "插入${dataNum}条数据"
    dataNum = 1
    listUrl = "https://m.cpzx18.com/v1/lottery/openResult?lotteryCode=1412&dataNum=${dataNum}&"
    Thread.sleep(dataNum * 5 * 60 * 1000)
}



sql.close()