package info.puton.product.smartsearch.service;

/**
 * Created by taoyang on 2016/9/21.
 */
public interface FileStorage {

    void init() throws Exception;

    void put(String filePath, String key) throws Exception;

    void get(String fileLocation, String key) throws Exception;

}
