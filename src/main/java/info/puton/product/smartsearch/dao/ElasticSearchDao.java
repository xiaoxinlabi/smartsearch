package info.puton.product.smartsearch.dao;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by taoyang on 2016/9/22.
 */
@Repository
public class ElasticSearchDao {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public void createIndex(String index, String type, String id, Map data){
        IndexResponse response = elasticsearchTemplate.getClient().prepareIndex(index, type, id)
                .setSource(data)
                .get();
        System.out.println("Index created. id:" + response.getId());
    }

    public void deleteIndex(String index, String type, String id){
        DeleteResponse response = elasticsearchTemplate.getClient().prepareDelete(index, type, id).get();
        System.out.println("Index deleted. id:" + response.getId());
    }

}
