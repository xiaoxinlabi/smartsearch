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
        website.setId("puton");
        website.setUrl("http://puton.info");
        website.setTitle("扑通");
        website.setKeywords("扑通,puton");
        website.setDescription("puton-心跳的声音");
        website.setContent("扑通扑通扑通扑通扑通扑通扑通扑通扑通扑通扑通扑通");
        websiteIndexer.addWebsite(website);
    }

}