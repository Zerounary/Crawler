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
import java.util.Date

//def url = 'jdbc:mysql://192.168.1.6:3306/lottery?useSSL=false'
def url = 'jdbc:mysql://localhost:3306/lottery?useSSL=false'
def user = 'root'
def password = 'toor'
def driver = 'com.mysql.jdbc.Driver'
def sql;
def dataNum = 200
def lotteryCodes = ['1412', '0101', '1407']
def doc, listUrl;
while (true){
    sql =  Sql.newInstance(url, user, password, driver);
    for (lotteryCode in lotteryCodes){
        listUrl = "https://m.cpzx18.com/v1/lottery/openResult?lotteryCode=${lotteryCode}&dataNum=${dataNum}&"
        doc = Jsoup.connect(listUrl).ignoreContentType(true).get();
        data = JSONObject.parseObject(doc.text()).getJSONArray('data')
        println new Date().toLocaleString() + "\t${lotteryCode}\t获取${dataNum}条数据"
        for (it in data){
            if (sql.firstRow("SELECT COUNT(*) AS num FROM lotteryhis where issue =${it.issue} and lotteryCode=${lotteryCode}").num == 0){
                sql.executeInsert"""
              INSERT INTO lotteryhis(issue, lotteryCode, createdTime, open, openNumber, openTime)
              VALUES (${it.issue},${it.lotteryCode},${it.createdTime},${it.open},${it.openNumber},${it.openTime})
            """;
                println new Date().toLocaleString()  + "\t${lotteryCode}\t${it.issue}期数据已插入,结果: ${it.openNumber}"
            }else{
                println new Date().toLocaleString()  + "\t${lotteryCode}\t${it.issue}期的数据已存在"
            }
        }
        listUrl = "https://m.cpzx18.com/v1/lottery/openResult?lotteryCode=${lotteryCode}&dataNum=${dataNum}&"
    }
    Thread.sleep(30 * 1000)
    dataNum = 10
    sql.close()
}

