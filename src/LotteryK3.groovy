/**
 * Created by Zerounary on 2019/6/4.
 */

def final WATCH_POINT = 2
def final FIRST_BET_AMT = 1;
def final ODDS = 1.97
def final EARNING_RATE = (2 - ODDS)/ODDS
def final FLOAT_AMT = 0
def final POSITIVE = 'd'
def final NEGATIVE = 'x'

def hisLotterys = []
def hisBets = []
def guessReuslt = [:]
guessReuslt[POSITIVE] = NEGATIVE
guessReuslt[NEGATIVE] = POSITIVE
def lastResult;
def input = new Scanner(System.in)
def set = new HashSet()
def betResult
def totBetAmt = 0;
def betAmt = 0;
while(true){
    print "输入结果: "
    lastResult = input.nextLine();
    hisLotterys << lastResult
    if(hisLotterys.size() > WATCH_POINT){
        set.addAll(hisLotterys[-1..-WATCH_POINT])
        if(set.size() == 1){
            if(hisBets.size() == 0){
                betResult = guessReuslt[hisLotterys[-1]]
                hisBets << FIRST_BET_AMT
                println "下${betResult} ${FIRST_BET_AMT}倍"
            }else{
                totBetAmt = hisBets.sum()
                betAmt = totBetAmt + Math.ceil(EARNING_RATE * totBetAmt) + FLOAT_AMT
                hisBets << betAmt
                println "下${betResult} ${betAmt}倍"
            }
        }else{
            hisBets.clear()
            set.clear()
            println "不下注"
        }
    }else{
        println '不下注'
    }
}