package info.puton.product.smartsearch.service;

import info.puton.product.common.feature.test.TestSupport;
import info.puton.product.smartsearch.model.Address;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by taoyang on 2016/10/11.
 */
public class AddressIndexerTest extends TestSupport {

    @Autowired
    AddressIndexer addressIndexer;

    @Test
    public void testInitAddress() throws Exception {
        addressIndexer.initAddress();
    }

    @Test
    public void testAddAddress() throws Exception {
        Address address = new Address();
        address.setId("yt255014");
        address.setType("yt255014");
        address.setChineseName("陶阳");
        address.setEnglishName("taoyang");
        address.setMobilePhone("15001971346");
        addressIndexer.addAddress(address);
    }

    @Test
    public void testDeleteAddress() throws Exception {

    }
}