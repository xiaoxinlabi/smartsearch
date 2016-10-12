package info.puton.product.smartsearch.service;

import info.puton.product.common.feature.test.TestSupport;
import info.puton.product.smartsearch.model.Website;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by taoyang on 2016/10/12.
 */
public class WebsiteIndexerTest extends TestSupport {

    @Autowired
    WebsiteIndexer websiteIndexer;

    @Test
    public void testInitWebsite() throws Exception {
        websiteIndexer.initWebsite();
    }

    @Test
    public void testAddWebsite() throws Exception {
        Website website = new Website();
        website.setId("ifhug");
        website.setUrl("http://ifhug.com");
        website.setTitle("i站");
        website.setKeywords("i站,ifhug,朋友圈,家,世界");
        website.setDescription("i站-朋友圈改变世界");
        website.setContent("巴拉巴拉巴拉巴拉咕噜咕噜咕噜咕噜");
        websiteIndexer.addWebsite(website);
    }

    @Test
    public void testDeleteWebsite() throws Exception {

    }
}