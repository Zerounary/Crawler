import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import org.jsoup.Jsoup

/**
 * Created by Zerounary on 2019/6/6.
 */
class LotteryBet {
  static String bet(cookie, code, issue, betResult, betAmt){
    JSONArray postData = new JSONArray();
    JSONObject object = new JSONObject();
    object.put("bettingAmount",betAmt); //支持表达式，字符串会自动转数字
    object.put("bettingIssue",issue);
    object.put("bettingNumber", "${betResult},".toString());
    object.put("bettingCount",1);
    object.put("bettingPoint", "8.5");
    object.put("bettingUnit", 1);
    object.put("graduationCount",1);
    object.put("lotteryCode", "${code}".toString());
    object.put("playDetailCode","${code}A10".toString());
    postData.add(object)
//    println postData.toJSONString()
    def doc = Jsoup.connect("https://m.cpzx18.com/v1/betting/addBetting")
            .timeout(1000000)
            .ignoreContentType(true)
            .cookie("JSESSIONID","${cookie}")
            .referrer("https://m.cpzx18.com/lottery/K3/${code}")
            .data("bettingData", postData.toJSONString())
            .post()
    return JSONObject.parseObject(doc.text()).getString("msg")
  }
}
