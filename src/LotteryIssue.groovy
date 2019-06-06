import java.text.SimpleDateFormat

/**
 * Created by Zerounary on 2019/6/6.
 */

class LotteryIssue {
  def static String nextIssue(lastOpenTime){
    def datePaser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    def dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
    def issueDateFormat = new SimpleDateFormat("yyyyMMdd");
    def lastOpenDate = datePaser.parse(lastOpenTime);
    def nextOpenDate = new Date(lastOpenDate.time + (1000 * 60))
    def beginDateOfNextOpen = datePaser.parse(dateFormat.format(nextOpenDate) + "00:00:00")
    def issueNo = String.valueOf((int)((nextOpenDate.time - beginDateOfNextOpen.time)/1000/60))
    issueNo = issueDateFormat.format(beginDateOfNextOpen) + ("0" * (4 - issueNo.length())) + issueNo
    return issueNo
  }
}
