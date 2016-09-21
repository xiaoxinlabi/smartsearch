package info.puton.product.smartsearch.service;

/**
 * Created by taoyang on 2016/9/21.
 */
public interface HBaseFileOperator {

    void putFile(String filePath, String key) throws Exception;

    void getFile(String fileLocation, String key) throws Exception;

}
