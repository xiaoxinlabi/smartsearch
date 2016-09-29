package info.puton.product.smartsearch.service;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Created by taoyang on 2016/9/21.
 */
public interface SmartSearchHandler {

    void initSearch() throws Exception;

    void handleFile(String filePath) throws Exception;

}
