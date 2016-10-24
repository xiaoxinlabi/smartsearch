package info.puton.customize.hsb.mocker;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by taoyang on 2016/10/17.
 */
public class LoginMocker {

    /**
     * 根据URL获得所有的html信息
     * @param url
     * @return
     */
    public String getHtmlByUrl(String url) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(httpGet);
//        System.out.println(response.getStatusLine());
        HttpEntity entity = response.getEntity();
        String html = EntityUtils.toString(entity);
        EntityUtils.consume(entity);
        response.close();
        return html;
    }

    public Map getCookie(String url, Map params) throws IOException {
        Map resultMap = new HashMap();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<>();
        Set<String> keySet = params.keySet();
        for(String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
        }
        HttpEntity httpEntity = new UrlEncodedFormEntity(nvps);
        httpPost.setEntity(httpEntity);
        HttpClientContext context = HttpClientContext.create();
        CookieStore cookieStore = new BasicCookieStore();
        context.setCookieStore(cookieStore);

        CloseableHttpResponse response = httpclient.execute(httpPost,context);
        HttpEntity entity = response.getEntity();
        String html = EntityUtils.toString(entity);
//        System.out.println(html);
        cookieStore = context.getCookieStore();
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie : cookies) {
            resultMap.put(cookie.getName(),cookie.getValue());
            System.out.println(cookie.getName()+":"+cookie.getValue());
        }
        EntityUtils.consume(entity);
        response.close();
        return resultMap;
    }

    public static void main(String[] args) {

        LoginMocker loginMocker = new LoginMocker();

        String host = "http://portal.hsbank.com";

        try {
            String html = loginMocker.getHtmlByUrl(host+"/wps/myportal/");
            Document doc = Jsoup.parse(html);
            Element form = doc.select("body > form").first();
            String url = form.attr("action");
//            System.out.println(url);

            Map params = new HashMap();
            params.put("userid","wushuang");
            params.put("password","11111111");
            loginMocker.getCookie(host+url,params);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}