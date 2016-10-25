package info.puton.product.smartsearch.service;

/**
 * Created by taoyang on 2016/9/21.
 */
public interface BaseIndexer {

    void deleteIndex(String index);

    void deleteDocument(String index, String type, String id);

}
