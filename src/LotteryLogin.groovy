/**
 * Created by Zerounary on 2019/6/3.
 */
// https://mvnrepository.com/artifact/net.sourceforge.htmlunit/htmlunit
import com.gargoylesoftware.htmlunit.util.Cookie
// https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient

//@GrabConfig(systemClassLoader=true)
//@Grapes(
//        @Grab(group='net.sourceforge.htmlunit', module='htmlunit', version='2.35.0'),
//        @Grab(group='org.apache.httpcomponents', module='httpclient', version='4.5.5')
//)
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;


WebClient webClient = new WebClient(BrowserVersion.CHROME);

//参数设置
// 1 启动JS
webClient.getOptions().setJavaScriptEnabled(true);
// 2 禁用Css，可避免自动二次请求CSS进行渲染
webClient.getOptions().setCssEnabled(false);
//3 启动客户端重定向
webClient.getOptions().setRedirectEnabled(true);
// 4 运行错误时，是否抛出异常
webClient.getOptions().setThrowExceptionOnScriptError(false);
// 5 设置超时
webClient.getOptions().setTimeout(5000000);
//6 设置忽略证书
//webClient.getOptions().setUseInsecureSSL(true);
//7 设置Ajax
//webClient.setAjaxController(new NicelyResynchronizingAjaxController());
//8设置cookie
webClient.getCookieManager().setCookiesEnabled(true);

//获取页面
HtmlPage page = webClient.getPage("https://m.cpzx18.com/login");
println page.asXml()
HtmlTextInput username = (HtmlTextInput)page.getByXPath("//*[@id='app']/div[1]/div/div/div/form/div/div[1]/div/div/input").get(0);
username.setValueAttribute("Hidro");
HtmlTextInput password = (HtmlTextInput)page.getByXPath("//*[@id='app']/div[1]/div/div/div/form/div/div[2]/div/div/input").get(0);
password.setValueAttribute("HIDRO123");
HtmlButton button = (HtmlButton)page.getByXPath("//button").get(0);
HtmlPage retPage = (HtmlPage) button.click();
webClient.waitForBackgroundJavaScript(1000000);
//获取cookie
Set<Cookie> cookies = webClient.getCookieManager().getCookies();;
Map<String, String> responseCookies = new HashMap<String, String>();
for (Cookie c : cookies) {
    responseCookies.put(c.getName(), c.getValue());
    System.out.print(c.getName()+":"+c.getValue());
}

//关闭webclient
webClient.close();

