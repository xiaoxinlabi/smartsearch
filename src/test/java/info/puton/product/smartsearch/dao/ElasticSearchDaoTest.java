package info.puton.product.smartsearch.dao;

import info.puton.product.common.feature.test.TestSupport;
import info.puton.product.smartsearch.constant.Index;
import info.puton.product.smartsearch.constant.Type;
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

    @Test
    public void testDeleteIndex() throws Exception {
//        String id = "0cc0d86a-9243-4866-9011-bd4a66dc3e12";
        String id = "a59b6a3f-35dc-4e9c-ba35-dc6519368cf1";
        elasticSearchDao.deleteIndex(Index.SMART_SEARCH, Type.FILE_FULL_TEXT, id);
    }
}