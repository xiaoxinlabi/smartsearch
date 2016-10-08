package info.puton.product.smartsearch.dao;

import info.puton.product.smartsearch.constant.Field;
import info.puton.product.smartsearch.constant.Index;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by taoyang on 2016/9/30.
 */
@Repository
public class SmartSearchDao {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public void highlightQuery(String keyword){
        QueryBuilder qb = QueryBuilders.multiMatchQuery(
                keyword,
                Field.FILE_NAME,
                Field.CONTENT);
        SearchRequestBuilder srb = elasticsearchTemplate.getClient().prepareSearch(Index.FILE_FULL_TEXT);

        srb.setQuery(qb);

        srb.addHighlightedField(Field.FILE_NAME);
        srb.setHighlighterPreTags("<em>");
        srb.setHighlighterPostTags("</em>");

        srb.addHighlightedField(Field.CONTENT);
        srb.setHighlighterPreTags("<em>");
        srb.setHighlighterPostTags("</em>");

        SearchResponse sr = srb.execute().actionGet();
        System.out.println(sr);
    }

}
