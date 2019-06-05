/**
 * Created by Zerounary on 2019/6/4.
 */
class K3 {
    private WATCH_POINT = 2
    private final FIRST_BET_AMT = 1;
    private final ODDS = 1.97
    private final EARNING_RATE = (2 - ODDS)/ODDS
    private final FLOAT_AMT = 0
    private final POSITIVE = '大'
    private final NEGATIVE = '小'

    private pastLotterys = []
    private hisBets = []
    private guessReuslt = [("${POSITIVE}".toString()):NEGATIVE, ("${NEGATIVE}".toString()):POSITIVE]
    private betResult = ""
    private totBetAmt = 0;
    private betAmt = 0;
    public K3(){}
    public K3(wacthPoint){
        this.WATCH_POINT = wacthPoint
    }
    K3Result  nextStep(lastOpenResult){
        K3Result k3Result = new K3Result()
        pastLotterys << lastOpenResult
        if(pastLotterys.size() > WATCH_POINT){
            def isSameOpenReusltEveryInWatch = pastLotterys[-1..-WATCH_POINT].every{it==pastLotterys[-1]}
            if(isSameOpenReusltEveryInWatch){
                if(hisBets.size() == 0){
                    betResult = guessReuslt[pastLotterys[-1]]
                    betAmt = FIRST_BET_AMT
                }else{
                    totBetAmt = hisBets.sum()
                    betAmt = totBetAmt + Math.ceil(EARNING_RATE * totBetAmt) + FLOAT_AMT
                }
                hisBets << betAmt
                k3Result.isBet = true;
                k3Result.betResult = betResult;
                k3Result.betAmt = betAmt
//                println "下${betResult} ${betAmt}倍"
            }else{
                hisBets.clear()
                k3Result.isBet = false;
//                println "不下注"
            }
        }else{
            k3Result.isBet = false;
//            println '不下注'
        }

        return k3Result;
    }
    class K3Result{
        Boolean isBet;
        String  betResult;
        Long    betAmt;
    }
}
