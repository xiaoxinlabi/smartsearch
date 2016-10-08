package info.puton.product.smartsearch.dao;

import info.puton.product.smartsearch.constant.Field;
import info.puton.product.smartsearch.constant.Index;
import info.puton.product.smartsearch.constant.Type;
import info.puton.product.smartsearch.model.BaseSearchResult;
import info.puton.product.smartsearch.model.FileFullText;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by taoyang on 2016/9/30.
 */
@Repository
public class SmartSearchDao {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Value("#{settings['maxContentLength']}")
    private Integer maxContentLength;

    public List<BaseSearchResult> highlightQuery(Map params){

        String keyword = (String) params.get("keyword");
        String filterType = (String) params.get("type");

        QueryBuilder qb = QueryBuilders.multiMatchQuery(
                keyword,
                Field.FILE_NAME,
                Field.CONTENT);
        SearchRequestBuilder srb = elasticsearchTemplate.getClient().prepareSearch(Index.FILE_FULL_TEXT);

        srb.setQuery(qb);

        if(filterType!=null && !"".equals(filterType)){
            if(filterType.equals(Type.DOC)){
                srb.setTypes(Type.DOC, Type.DOCX);
            } else if(filterType.equals(Type.XLS)){
                srb.setTypes(Type.XLS, Type.XLSX);
            } else if(filterType.equals(Type.PPT)){
                srb.setTypes(Type.PPT, Type.PPTX);
            } else {
                srb.setTypes(filterType);
            }
        }

        srb.addHighlightedField(Field.FILE_NAME);
        srb.addHighlightedField(Field.CONTENT);
//        srb.setHighlighterPhraseLimit(maxContentLength);
        srb.setHighlighterPreTags("<span>");
        srb.setHighlighterPostTags("</span>");
        srb.setHighlighterFragmentSize(maxContentLength);

        SearchResponse sr = srb.execute().actionGet();
//        System.out.println(sr);

        List<BaseSearchResult> resultList = new ArrayList<>();
        SearchHit[] searchHits = sr.getHits().getHits();
        for (SearchHit searchHit : searchHits) {
            String index = searchHit.getIndex();
            String type = searchHit.getType();
            String id = searchHit.getId();
            Float score = searchHit.getScore();
            if(type.equals(Type.DOC)
                    || type.equals(Type.DOCX)
                    || type.equals(Type.XLS)
                    || type.equals(Type.XLSX)
                    || type.equals(Type.PPT)
                    || type.equals(Type.PPTX)
                    || type.equals(Type.PDF)
                    || type.equals(Type.TXT)
                    ){
                FileFullText result = new FileFullText();
                result.setIndex(index);
                result.setType(type);
                result.setId(id);
                result.setScore(score);

                String fileName = (String) searchHit.getSource().get("fileName");
                String author = (String) searchHit.getSource().get("author");
                String modifyDate = (String) searchHit.getSource().get("modifyDate");
                String content = (String) searchHit.getSource().get("content");
                Map<String, HighlightField> highlightFields = searchHit.highlightFields();
                if(highlightFields.containsKey("fileName")){
                    Text[] highlightedFileNames = highlightFields.get("fileName").getFragments();
                    fileName = highlightedFileNames[0].toString();
                }
                if(highlightFields.containsKey("content")){
                    Text[] highlightedContents = highlightFields.get("content").getFragments();
                    content = highlightedContents[0].toString();
                } else {
                    if(content.length()> maxContentLength){
                        content = content.substring(0,maxContentLength);
                    }
                }

//                System.out.println(fileName);
//                System.out.println(content);

                result.setFileName(fileName);
                result.setAuthor(author);
                result.setModifyDate(modifyDate);
                result.setContent(content);

                resultList.add(result);
            }

        }
        return resultList;
    }

}
