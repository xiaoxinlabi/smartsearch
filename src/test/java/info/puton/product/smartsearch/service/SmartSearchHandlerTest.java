package info.puton.product.smartsearch.service;

import info.puton.product.common.feature.test.TestSupport;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by taoyang on 2016/9/21.
 */
public class SmartSearchHandlerTest extends TestSupport {

    @Autowired
    SmartSearchHandler smartSearchHandler;

    @Test
    public void testInitFile() throws Exception {
        smartSearchHandler.initFile();
    }

    @Test
    public void testHandleFile() throws Exception {
        String filePath1 = "D:/programming/java/product/smartsearch/src/test/resources/一碗阳春面.docx";
        smartSearchHandler.handleFile(filePath1);
        String filePath2 = "D:/programming/java/product/smartsearch/src/test/resources/Hbase简介.pptx";
        smartSearchHandler.handleFile(filePath2);
    }

}