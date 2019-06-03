import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import com.alibaba.fastjson.JSONObject
import Tools
import org.jsoup.nodes.Element

/**
 * 2019年5月31日
 * 群里面发了一些【幸运飞艇】相关的赚钱截图，还贴出来了【导师】的二维码，结果却是一个链接
 *
 */

def urls = ['http://a.c41ln.cn/', 'http://b.c41ln.cn/', 'http://c.c41ln.cn/', 'http://d.c41ln.cn/', 'http://e.c41ln.cn/', 'http://f.c41ln.cn/', 'http://m.c41ln.cn/']
def agent="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)" + "  Chrome/56.0.2924.87 Safari/537.36" ;
System.setProperty("proxySet", "true");
System.getProperties().put("http.proxyHost", '112.85.168.202');
System.getProperties().put("http.proxyPort", '9999');
def a_array = []
def isFind = false;
def his = []
def mol = 0.0
def TOP_LINE = 0.9
def doc;
for (url in urls){
    println url
    while (true){
        def wx = [:];
        try{
//            doc = Jsoup.connect('http://2019.ip138.com/ic.asp')
//                    .ignoreContentType(true)
//                    .userAgent(agent)
//                    .ignoreHttpErrors(true)
//                    .timeout(3000)
//                    .get();
//            print doc.html()
//            return;
            doc = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .userAgent(agent)
                    .ignoreHttpErrors(true)
                    .timeout(3000)
                    .get();
            wx.org = doc.select('#nickname > h4:nth-child(1)').text();
            wx.nickname =  doc.select('#nickname > h4:nth-child(2)').text()
            wx.sex = doc.select('#sex').attr('src').toString().replace('.png','')
            wx.city = doc.select('#nickname > p > span').text()
            wx.imageurl = doc.head().html().find('http://img.*\\.jpg')
            wx.username = doc.select('#footer > a > h4').text().replace('微信：','').replace(' (点击复制)','');

            //检查是否已存在
            isFind = false
            for (a in a_array){
                if (a.username == wx.username){
                    isFind = true
                    println(wx.username + '重复')
                    break
                }
            }
            if(isFind){
                his.add(1)
            }else{
                his.add(0)
                a_array.add(wx)
                println('新增' + wx.username)
                println wx
            }
            mol = 0.0;
            for (i in his){
                if(i==1){
                    mol = mol + 1
                }
            }
            if(mol/his.size() > TOP_LINE){
                break;
            }
            println('====>当前指数' + mol/his.size())
        }catch (HttpStatusException e){
            print(e.getMessage())
        }catch(Exception e){
            print(e.getMessage())
        }
    }
}

Tools.saveStringToFile('desktop','微信',JSONObject.toJSONString(a_array))

