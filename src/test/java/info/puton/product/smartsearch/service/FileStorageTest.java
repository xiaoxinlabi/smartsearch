package info.puton.product.smartsearch.service;

import info.puton.product.common.feature.test.TestSupport;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by taoyang on 2016/9/29.
 */
public class FileStorageTest extends TestSupport {

    @Autowired
    FileStorage hbfo;

    @Test
    public void testInit() throws Exception {
        hbfo.init();
    }

    @Test
    public void testPut() throws Exception {
        String filePath = "src/main/resources/一碗阳春面.docx";
        hbfo.put(filePath, "a00002");
    }

    @Test
    public void testGet() throws Exception {
        hbfo.get("E:/tmp/yang", "a00002");
    }
}