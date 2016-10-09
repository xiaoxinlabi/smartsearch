package info.puton.product.smartsearch.dao;

import info.puton.product.common.feature.test.TestSupport;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by taoyang on 2016/9/30.
 */
public class SmartSearchDaoTest extends TestSupport {

    @Autowired
    SmartSearchDao smartSearchDao;

    @Test
    public void testQueryResult() throws Exception {

        Map params = new HashMap();
        params.put("keyword","陶阳");
//        params.put("keyword","阳春面");
//        params.put("keyword","检索");
//        params.put("keyword","简介");
        System.out.println(smartSearchDao.queryResult(params));

    }
}