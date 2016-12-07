package info.puton.product.smartsearch.dao;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by taoyang on 2016/9/22.
 */
@Repository
public class ElasticSearchDao {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public void createIndex(String index){
        CreateIndexResponse response = elasticsearchTemplate.getClient().admin().indices().prepareCreate(index)
                .get();
//        System.out.println("Index created. index:" + index);
    }

    public void createSchema(String index, String type, String source){
        PutMappingResponse response = elasticsearchTemplate.getClient().admin().indices().preparePutMapping(index)
                .setType(type)
                .setSource(source)
                .get();
//        System.out.println("Schema created. index:" + index + " type:" + type);
    }

    public void deleteIndex(String index){
        DeleteIndexResponse response = elasticsearchTemplate.getClient().admin().indices().prepareDelete(index).get();
//        System.out.println("Index deleted. index:" + index);
    }

    public void createDocument(String index, String type, String id, Map data){
        IndexResponse response = elasticsearchTemplate.getClient().prepareIndex(index, type, id)
                .setSource(data)
                .get();
//        System.out.println("Index created. id:" + response.getId());
    }

    public void deleteDocument(String index, String type, String id){
        DeleteResponse response = elasticsearchTemplate.getClient().prepareDelete(index, type, id).get();
//        System.out.println("Index deleted. id:" + response.getId());
    }

    public Map getDocument(String index, String type, String id){
        GetResponse response = elasticsearchTemplate.getClient().prepareGet(index, type, id).get();
        Map source = response.getSourceAsMap();
        return source;
    }

}
