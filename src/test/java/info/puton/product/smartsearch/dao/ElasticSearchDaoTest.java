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
    public void testCreateSchema() throws Exception {
        String source = "{\n" +
                "    \"" + Type.DOC  + "\": {\n" +
                "      \"properties\": {\n" +
                "        \"fileName\": {\n" +
                "          \"type\": \"string\",\n" +
                "          \"analyzer\": \"ik\"\n" +
                "        },\n" +
                "        \"author\": {\n" +
                "          \"type\": \"string\",\n" +
                "          \"analyzer\": \"ik\"\n" +
                "        },\n" +
                "        \"content\": {\n" +
                "          \"type\": \"string\",\n" +
                "          \"analyzer\": \"ik\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }";
        elasticSearchDao.createSchema(Index.FILE_FULL_TEXT, Type.DOC, source);
    }

    @Test
    public void testDeleteSchema() throws Exception {
        elasticSearchDao.deleteSchema(Index.FILE_FULL_TEXT);
    }

    @Test
    public void testCreateIndex() throws Exception {
        Map data = new HashMap();
        data.put("key1","value1");
        data.put("key2",2);
        elasticSearchDao.createIndex("myindex","mytype","myid1",data);
    }

}