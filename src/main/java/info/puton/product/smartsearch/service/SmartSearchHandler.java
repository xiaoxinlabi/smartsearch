package info.puton.product.smartsearch.service;

import info.puton.product.smartsearch.model.Address;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Map;

/**
 * Created by taoyang on 2016/9/21.
 */
public interface SmartSearchHandler {

    void initFile() throws Exception;

    void handleFile(String filePath, Map additional) throws Exception;

    void handleAddress(Address address) throws Exception;

}
