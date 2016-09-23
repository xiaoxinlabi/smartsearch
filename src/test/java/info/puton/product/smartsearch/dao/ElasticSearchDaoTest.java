package info.puton.product.smartsearch.dao;

import info.puton.product.common.feature.test.TestSupport;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by taoyang on 2016/9/22.
 */
public class ElasticSearchDaoTest extends TestSupport {

    @Autowired
    ElasticSearchDao elasticSearchDao;

    @Test
    public void testCreateIndex() throws Exception {

        Map data = new HashMap();
        data.put("key1","value1");
        data.put("key2",2);
        elasticSearchDao.createIndex("myindex","mytype","myid1",data);

    }
}