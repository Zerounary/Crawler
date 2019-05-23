import groovy.json.JsonOutput
import org.jsoup.Jsoup
import com.alibaba.fastjson.JSONObject
import Tools

def baseUrl = 'http://ecph.cnki.net/'
def url = baseUrl + 'browse.aspx';
def points = []
Jsoup.connect(url).get().select('.b_bot').each {
    def catagray = it.select('.title').text();
    println "catagray: $catagray"
    it.select('.textbot > a').each { inIt ->
        def title = inIt.text();
        def href = baseUrl + "Allword.aspx?vol=" + Tools.stringToUnicode('%u',title).replaceAll('/•/g',' ')
        def treeHref = 'http://ecph.cnki.net/treefile/' + URLEncoder.encode(title) + '.htm?ObjID='
        println "title: $title"
        println "href: $href"
        def point = [:]
        point.catagray = catagray
        point.title = title
        point.href = href
        point.treeHref = treeHref
        points.add(point)
    }
}
Tools.saveStringToFile('javaCrawler','ecph.cnki.net', JSONObject.toJSONString(points))

