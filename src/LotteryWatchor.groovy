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

def cookie = "D234FEE31BF0CFBDF1387CD052629470";
def LENGTH_OF_SAME_OPENRESULT = 7
def NUM_OF_REQUEST_DATA = 10
def REQUEST_TIME_BY_SECONDS = 10
def VOICE_NOTICE_REPEAT_TIMES = 3
def TOP_LIMIT_BET_AMT = 3
//def readyToBuyLotterys = ['1412', '0101', '1407']
def readyToBuyLotterys = ['1407']
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
        def k3 = new K3(LENGTH_OF_SAME_OPENRESULT)
        def k3Result
        //要注意K3的序列和接口提供的排序是反的
        for(perIssue in pastLotterys.reverse()){
          k3Result = k3.nextStep(perIssue.openResult)
        }
        if(pastLotterys[0..LENGTH_OF_SAME_OPENRESULT].every{it.openResult==pastLotterys[0].openResult}){
          def decide = [:]
          decide.lotteryCode = lotteryCode
          //期号序列生长器目前只支持1407
          decide.issue = LotteryIssue.nextIssue(pastLotterys[0]["openTime"])
          decide.betResult = k3Result.betResult;
          decide.betAmt = (k3Result.betAmt > TOP_LIMIT_BET_AMT) ? 0 : k3Result.betAmt;
          decideToBuyLotterys << decide
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
    //实际购买的时候，0101要考虑时间太短买不到的问题
    for(decide in decideToBuyLotterys){
      def replay
      println "====> ${decide.lotteryCode}\t${decide.issue}期\t${decide.betResult}\t${decide.betAmt}倍"
      while(true){
        try{
          replay = LotteryBet.bet(cookie, decide.lotteryCode, decide.issue, decide.betResult, decide.betAmt )
          break;
        }catch (Exception e){
          println e.getMessage()
          println "Retrying bet..."
          //"say 下注时出错，正在重试".execute()
        }
      }
      ("say " + "${decide.lotteryCode},${decide.betResult},${decide.betAmt}倍,${replay}").execute()
    }
//    ("say " + Collections.nCopies(VOICE_NOTICE_REPEAT_TIMES, "${decideToBuyLotterys.join(',')} 已出现数据").join(",重复,") ).execute()
    decideToBuyLotterys.clear()
  }
  Thread.sleep(((60 + REQUEST_TIME_BY_SECONDS) - (((long)(System.currentTimeMillis()/1000)) % 60)) * 1000)
}

