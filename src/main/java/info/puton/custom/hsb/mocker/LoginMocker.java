package info.puton.custom.hsb.mocker;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * Created by taoyang on 2016/10/17.
 */
@Component
public class LoginMocker {

    private String host = "http://portal.hsbank.com";

    @Value("#{settings['portalUserid']}")
    private String userid;

    @Value("#{settings['portalPassword']}")
    private String password;

//    private String userid = "wushuang";
//    private String password = "11111111";

    /**
     * 根据URL获得所有的html信息
     * @param url
     * @return
     */
    public String getHtmlByUrl(String url) throws IOException {
        HttpClient client = new HttpClient();
        // 设置代理服务器地址和端口
        //client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
        // 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https
        HttpMethod method=new GetMethod(url);
        //使用POST方法
        //HttpMethod method = new PostMethod("http://java.sun.com");
        client.executeMethod(method);
        //打印服务器返回的状态
//        System.out.println(method.getStatusLine());
        //打印返回的信息
//        System.out.println(method.getResponseBodyAsString());
        String html = method.getResponseBodyAsString();
        //释放连接
        method.releaseConnection();
        return html;
    }


    public boolean valid(String userid, String password) throws IOException {

        String loginPageHtml = getHtmlByUrl(host + "/wps/myportal/");

        Document doc = Jsoup.parse(loginPageHtml);
        Element form = doc.select("body > form").first();
        String url = form.attr("action");

        HttpMethod method=new PostMethod(host + url);
        NameValuePair nvp1 = new NameValuePair( "userid" , userid );
        NameValuePair nvp2 = new NameValuePair( "password" , password );
        NameValuePair[] nvps = {nvp1,nvp2};
        method.setQueryString(nvps);
        HttpClient client = new HttpClient();
        client.executeMethod(method);
        int statusCode = method.getStatusCode();
        method.releaseConnection();
        if (statusCode == 302){
            return true;
        }else{
            return false;
        }
    }

    public static void main(String[] args) {
        LoginMocker loginMocker = new LoginMocker();
        try {
//            System.out.println(loginMocker.getHtmlByUrl("http://portal.hsbank.com/wps/myportal/"));
            System.out.println(loginMocker.valid("wangyusu","password"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
