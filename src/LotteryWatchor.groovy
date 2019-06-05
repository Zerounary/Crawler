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

def LENGTH_OF_SAME_OPENRESULT = 1
def NUM_OF_REQUEST_DATA = 10
def REQUEST_TIME_BY_SECONDS = 10
def VOICE_NOTICE_REPEAT_TIMES = 3
def readyToBuyLotterys = ['1412', '0101', '1407']
def decideToBuyLotterys = []
while (true){
  for (lotteryCode in readyToBuyLotterys){
    def lotteryDataUrl = "https://m.cpzx18.com/v1/lottery/openResult?lotteryCode=${lotteryCode}&dataNum=${NUM_OF_REQUEST_DATA}&"
    while(true){
      try{
        def reponseDocument = Jsoup.connect(lotteryDataUrl).timeout(10000000).ignoreContentType(true).get();
        def pastLotterys = JSONObject.parseObject(reponseDocument.text()).getJSONArray('data')
        println new Date().toLocaleString() + "\t${lotteryCode}\t获取${NUM_OF_REQUEST_DATA}条数据"
        for(perIsuue in pastLotterys){
          def sumOfDiceNumbers = perIsuue.openNumber.split(",").sum({(Integer.parseInt(it))})
          perIsuue.openResult = (sumOfDiceNumbers > 10) ? "大":"小"
        }
        if(pastLotterys[0..LENGTH_OF_SAME_OPENRESULT].every{it.openResult==pastLotterys[0].openResult}){
          decideToBuyLotterys << lotteryCode
          println '-' * 100
          println CharPaint.getPaintString(lotteryCode)
          pastLotterys.eachWithIndex{ it,i ->
            println "${i}\t\t${it.issue}\t\t${it.openResult}"
          }
          println '-' * 100
        }
        break;
      }catch(Exception e){
        println e.getMessage()
        println "Retrying ..."
      }
    };
  }
  if(!decideToBuyLotterys.isEmpty()){
//    ("say " + Collections.nCopies(VOICE_NOTICE_REPEAT_TIMES, "${pastLotterys.join(',')} 已出现数据").join(",重复,") ).execute()
    decideToBuyLotterys.clear()
  }
  Thread.sleep(((60 + REQUEST_TIME_BY_SECONDS) - (((long)(System.currentTimeMillis()/1000)) % 60)) * 1000)
}

