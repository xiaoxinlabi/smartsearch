package info.puton.product.smartsearch.service;

import info.puton.product.common.feature.test.TestSupport;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by taoyang on 2016/11/30.
 */
public class AddressHandlerTest extends TestSupport {

    @Autowired
    AddressHandler addressHandler;

    @Test
    public void testHandleFile() throws Exception {
        Map additional = new HashMap();
        addressHandler.handleFile("D:\\documents\\Project\\HSB\\new\\address.xlsx", additional);
    }
}