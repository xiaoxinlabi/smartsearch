package info.puton.product.smartsearch.dao;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
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

    public void createSchema(String index, String type, String source){
        CreateIndexResponse response = elasticsearchTemplate.getClient().admin().indices().prepareCreate(index)
                .addMapping(type, source)
                .get();
        System.out.println("Schema created. headers:" + response.getHeaders());
    }

    public void deleteSchema(String index){
        DeleteIndexResponse response = elasticsearchTemplate.getClient().admin().indices().prepareDelete(index).get();
        System.out.println("Index deleted. headers:" + response.getHeaders());
    }

    public void createIndex(String index, String type, String id, Map data){
        IndexResponse response = elasticsearchTemplate.getClient().prepareIndex(index, type, id)
                .setSource(data)
                .get();
        System.out.println("Index created. id:" + response.getId());
    }

}
