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

def watch_level = 3
def dataNum = 10
def CHECK_TIME_WITH_SECONDS = 10
def REPEAT_TIMES = 3
def lotteryCodes = ['1412', '0101', '1407']
def todoCodes = []
def set = new HashSet()
def doc, listUrl;
String openNumber
def sum;
def obj;
def isRetry = true;
while (true){
    for (lotteryCode in lotteryCodes){
        listUrl = "https://m.cpzx18.com/v1/lottery/openResult?lotteryCode=${lotteryCode}&dataNum=${dataNum}&"
				while(isRetry){
						try{
							doc = Jsoup.connect(listUrl).timeout(10000000).ignoreContentType(true).get();
							data = JSONObject.parseObject(doc.text()).getJSONArray('data')
							println new Date().toLocaleString() + "\t${lotteryCode}\t获取${dataNum}条数据"
							for(it in data){
									openNumber = it.openNumber
									sum = openNumber.split(",").sum({(Integer.parseInt(it))})
									if(sum > 10){
											it.rst = '大'
									}else{
											it.rst = '小'
									}
							}
							for(i in 0..watch_level){
									set.add(data[i].rst)
							}
							if(set.size() == 1){
									println "-------------------------------------------------------------------------------"
									if(lotteryCode == '1407'){
											println """
												____  _       ______    
											 |  _ \\(_)     |  ____|   
											 | |_) |_  __ _| |__ __ _ 
											 |  _ <| |/ _` |  __/ _` |
											 | |_) | | (_| | | | (_| |
											 |____/|_|\\__, |_|  \\__,_|
																 __/ |          
																|___/    
									
											"""
											todoCodes << lotteryCode
									}else if(lotteryCode == '0101'){
											println """
														_    _ _    _ 
													 | |  | | |  | |
													 | |  | | |  | |
													 | |  | | |  | |
													 | |__| | |__| |
													 \\____/ \\____/ 
																					 
									
											"""
											todoCodes << lotteryCode
									}else if(lotteryCode == '1412'){
											println """
													 _____ 
													 | ____|
													 | |__  
													 |___ \\ 
														___) |
													 |____/  
									
											"""
											todoCodes << lotteryCode
									}
									for(i in 1..data.size()){
											obj = data[i-1]
											println "${i}\t\t${obj.issue}\t\t${obj.rst}"
									}
									println()
									println "-------------------------------------------------------------------------------"
							}

							set.clear()
							isRetry = false;

						}catch(Exception e){
							println e.getMessage()
							println "Retrying ..."
						}
					
				};
				isRetry = true;
    }
		if(!todoCodes.isEmpty()){
			for(i in 1..REPEAT_TIMES){
				"say ${todoCodes.join(',')} 已出现数据".execute();
				"say 重复".execute();
			}
			todoCodes.clear()
		}
    Thread.sleep(((60 + CHECK_TIME_WITH_SECONDS) - (((long)(System.currentTimeMillis()/1000)) % 60)) * 1000)
}

