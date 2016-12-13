package info.puton.product.smartsearch.service;

import java.util.Map;

/**
 * Created by taoyang on 2016/9/21.
 */
public interface BaseIndexer {

    void deleteIndex(String index);

    void deleteDocument(String index, String type, String id);

    Map getDocument(String index, String type, String id);

    void updateDocument(String index, String type, String id, Map fields);

}
