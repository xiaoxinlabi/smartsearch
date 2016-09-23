package info.puton.product.smartsearch.service;

import info.puton.product.common.feature.test.TestSupport;
import info.puton.product.smartsearch.model.FileFullText;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * Created by taoyang on 2016/9/21.
 */
public class SmartSearchHandlerTest extends TestSupport {

    @Autowired
    SmartSearchHandler smartSearchHandler;

    @Test
    public void testHandleFile() throws Exception {

        smartSearchHandler.handleFile();

    }
}