package info.puton.customize.hsb.crawler.pipeline;

import info.puton.product.smartsearch.model.Website;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by taoyang on 2016/10/17.
 */
public class PortalPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        Website website = resultItems.get("website");
        if (website!=null){
            System.out.println(website);
        }
    }
}
