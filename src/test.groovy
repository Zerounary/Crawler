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
//JSONArray postData = new JSONArray();
//JSONObject object = new JSONObject();
//object.put("bettingAmount","1"); //支持表达式，字符串会自动转数字
//object.put("bettingIssue","201906061141");
//object.put("bettingNumber", '大,');
//object.put("bettingCount",1);
//object.put("bettingPoint", "8.5");
//object.put("bettingUnit", 1);
//object.put("graduationCount",1);
//object.put("lotteryCode", "1407");
//object.put("playDetailCode","1407A10");
//postData.add(object)
//def doc = Jsoup.connect("https://m.cpzx18.com/v1/betting/addBetting")
//        .ignoreContentType(true)
//        .cookie("JSESSIONID","D234FEE31BF0CFBDF1387CD052629470")
//        .referrer("https://m.cpzx18.com/lottery/K3/1407")
//        .data("bettingData", postData.toJSONString())
//        .post()
//println doc.text()
//println LotteryIssue.nextIssue("2019-06-04 19:17:05")
LotteryBet.bet("D234FEE31BF0CFBDF1387CD052629470", "1407", "201906061162", "大" ,1)