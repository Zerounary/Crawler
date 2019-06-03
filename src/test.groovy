/**
 * Created by Zerounary on 2019/6/3.
 */

import com.alibaba.fastjson.JSONArray
import groovy.sql.Sql
import org.jsoup.Jsoup
import com.alibaba.fastjson.JSONObject

//def doc = Jsoup.connect("https://m.cpzx18.com/v1/balance/getTransactionRecordsFront?dateType=today&pageSize=10&pageNum=1&dictionId=-1&self=true&")
//        .ignoreContentType(true)
//        .cookie("JSESSIONID","F690DD3A097400227D6646F4CF753A00")
//        .referrer("https://m.cpzx18.com/userCenter")
//        .get()
JSONArray postData = new JSONArray();
JSONObject object = new JSONObject();
object.put("bettingAmount",0);
object.put("bettingCount",0);
object.put("bettingIssue","201906031224");
object.put("bettingNumber", '大');
object.put("bettingPoint", 0);
object.put("bettingUnit", 0);
object.put("graduationCount",0);
object.put("lotteryCode", "1407");
object.put("playDetailCode","1407A10");
postData.add(object)
def doc = Jsoup.connect("https://m.cpzx18.com/v1/betting/addBetting")
        .ignoreContentType(true)
        .cookie("JSESSIONID","F690DD3A097400227D6646F4CF753A00")
        .referrer("https://m.cpzx18.com/lottery/K3/1407")
        .data("bettingData", postData.toJSONString())
        .post()
println doc.text()