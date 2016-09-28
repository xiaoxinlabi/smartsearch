package info.puton.product.smartsearch.service;

import info.puton.product.smartsearch.model.FileFullText;

/**
 * Created by taoyang on 2016/9/21.
 */
public interface FileIndexer {

    void createIndex(FileFullText fileFullText);

    void initIndex();

}
