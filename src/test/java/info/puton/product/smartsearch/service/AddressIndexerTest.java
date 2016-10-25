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
        address.setAccountId("yt255014");
        address.setEnglishName("taoyang");
        address.setChineseName("陶阳");
        address.setFixedPhone("021-88888888");
        address.setMobilePhone("15001971346");
        address.setEmail("ty@puton.info");
        address.setAddress("上海市徐汇区宛平南路600号（上海市精神病医院）");
        address.setQq("66666666");
        address.setOrganization("Teradata");
        address.setDepartment("PS");
        address.setPosition("应用/大数据开发专家");
        address.setRemark("这个人很懒，但是有让他勤奋的理由。");
        addressIndexer.addAddress(address);
    }

}