package info.puton.customize.hsb.fetcher;

import info.puton.customize.hsb.mocker.LoginMocker;
import info.puton.product.smartsearch.util.FileUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;

import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

/**
 * Created by taoyang on 2016/11/4.
 */
@Component
public class OAFileFetcher {

    private String domain = "zhoa.hsbank.com";

    private Map cookieMap;

    public static final int cache = 10 * 1024;

    public OAFileFetcher() throws IOException {

        LoginMocker loginMocker = new LoginMocker();

        this.cookieMap = loginMocker.getCookie();

    }

    public void download(String url, String filePath) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        HttpClientContext context = HttpClientContext.create();
        CookieStore cookieStore = new BasicCookieStore();
        Set<String> keySet = cookieMap.keySet();
        for (String name : keySet) {
            BasicClientCookie cookie = new BasicClientCookie(name, (String) cookieMap.get(name));
            cookie.setDomain(domain);
            cookieStore.addCookie(cookie);
        }
        context.setCookieStore(cookieStore);

        CloseableHttpResponse response = httpclient.execute(httpGet,context);
        HttpEntity entity = response.getEntity();
        InputStream is = entity.getContent();
        String fileName = FileUtil.getFullFileName(url);
        filePath = filePath + "/" + fileName;
        System.out.println(fileName);
        File file = new File(filePath);
        try{
            file.getParentFile().mkdirs();
        }catch (Exception e){
            e.printStackTrace();
        }

        FileOutputStream fileOut = new FileOutputStream(file);
        byte[] buffer=new byte[cache];
        int ch = 0;
        while ((ch = is.read(buffer)) != -1) {
            fileOut.write(buffer,0,ch);
        }
        is.close();
        fileOut.flush();
        fileOut.close();
        EntityUtils.consume(entity);
        response.close();

    }

}
