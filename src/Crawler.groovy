import org.jsoup.Jsoup
import com.alibaba.fastjson.JSONObject
import Tools
import org.jsoup.nodes.Element

def url = 'http://zhongguose.com';
def lis = Jsoup.connect(url).get();
print(lis.html())
lis.each {
    print(it.text())
    it.select('a').each { iit ->
        print(iit.text())
    }
};
//Tools.saveStringToFile('javaCrawler','中国色',JSONObject.toJSONString(cls))

