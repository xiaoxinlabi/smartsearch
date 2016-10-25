package info.puton.product.smartsearch.service;

import info.puton.product.smartsearch.model.FileFullText;

import java.util.Map;

/**
 * Created by taoyang on 2016/9/21.
 */
public interface FileIndexer {

    void initFile();

    void addFile(FileFullText fileFullText);

}
