/**
 * Created by Zerounary on 2019/3/7.
 */

/***
 * 将一个中文字符传Unicode
 * @param prefix
 * @param str
 * @return
 */
static def stringToUnicode(String prefix, String str) {
    StringBuffer sb = new StringBuffer();
    char[] c = str.toCharArray();
    for (int i = 0; i < c.length; i++) {
        sb.append(prefix + Integer.toHexString((int)c[i]));
    }
    return sb.toString();
}

static def saveStringToFile(String dirKey,String name, String contentString){
    def defaultDir = [bigData:'D:/BigData/',javaCrawler:'D:/Programing software/eclipse_workspaces/works/WebCrawler', desktop:'C:\\Users\\Zerounary\\Desktop']
    new File(defaultDir[dirKey], name + '.json') << contentString
}