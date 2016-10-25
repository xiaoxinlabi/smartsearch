package info.puton.product.smartsearch.service;

import info.puton.product.smartsearch.model.Address;
import info.puton.product.smartsearch.model.Website;

/**
 * Created by taoyang on 2016/9/21.
 */
public interface WebsiteIndexer {

    void initWebsite();

    void addWebsite(Website website);

}
