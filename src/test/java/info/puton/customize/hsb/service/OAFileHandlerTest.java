package info.puton.customize.hsb.service;

import info.puton.product.common.feature.test.TestSupport;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by taoyang on 2016/11/8.
 */
public class OAFileHandlerTest extends TestSupport {

    @Autowired
    OAFileHandler oaFileHandler;

    @Test
    public void testPrepareFile() throws Exception {
        String dateStr = "2015-11-27";
        oaFileHandler.handleFile(dateStr);
    }
}