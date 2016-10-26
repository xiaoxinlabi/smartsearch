package info.puton.customize.hsb.crawler.pipeline;

import info.puton.product.smartsearch.model.Website;
import info.puton.product.smartsearch.service.WebsiteIndexer;
import info.puton.product.smartsearch.service.impl.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by taoyang on 2016/10/17.
 */
@Component()
public class PortalPipeline implements Pipeline {

    @Autowired
    WebsiteIndexer websiteIndexer;

    @Override
    public void process(ResultItems resultItems, Task task) {
        Website website = resultItems.get("website");
        if (website!=null && website.getTitle()!=null && !website.getTitle().equals("")){
//            System.out.println(website);
            String url = website.getUrl();
            String key = url;
            key = url.substring(url.length()-32);
            website.setId(key);
            websiteIndexer.addWebsite(website);
        }
    }
}
