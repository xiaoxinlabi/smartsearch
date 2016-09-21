package info.puton.product.smartsearch.service.impl;

import info.puton.product.common.feature.test.TestSupport;
import info.puton.product.smartsearch.service.FileStorage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by taoyang on 2016/9/21.
 */
public class HBaseServiceTest extends TestSupport {

    @Autowired
    FileStorage hbfo;

    @Test
    public void testInitStorage() throws Exception {
        hbfo.initStorage();
    }

    @Test
    public void testPutFile() throws Exception {
        String filePath = "D:/programming/java/practice/hbase-practice/src/main/resources/一碗阳春面.docx";
        hbfo.putFile(filePath, "a00002");
    }

    @Test
    public void testGetFile() throws Exception {
        hbfo.getFile("E:/tmp/yang", "a00002");
    }


}