package info.puton.product.smartsearch.service;

/**
 * Created by taoyang on 2016/9/21.
 */
public interface FileStorage {

    void initFile() throws Exception;

    void putFile(String filePath, String key) throws Exception;

    void getFile(String fileLocation, String key) throws Exception;

    void getFile(String fileLocation, String key, String rename) throws Exception;

}
