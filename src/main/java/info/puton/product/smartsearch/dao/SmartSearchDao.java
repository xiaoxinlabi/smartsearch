package info.puton.product.smartsearch.dao;

import info.puton.product.smartsearch.constant.Field;
import info.puton.product.smartsearch.constant.Index;
import info.puton.product.smartsearch.constant.Type;
import info.puton.product.smartsearch.model.BaseSearchResult;
import info.puton.product.smartsearch.model.FileFullText;
import info.puton.product.smartsearch.model.PageModel;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by taoyang on 2016/9/30.
 */
@Repository
public class SmartSearchDao {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Value("#{settings['maxHighlightedSize']}")
    private Integer maxHighlightedSize;

    public PageModel<BaseSearchResult> queryResult(Map params){

        String keyword = (String) params.get("keyword");
        String filterType = (String) params.get("type");
        Integer currentPage = (Integer) params.get("currentPage");
        Integer pageSize = (Integer) params.get("pageSize");

        //query
        QueryBuilder qb = QueryBuilders.multiMatchQuery(
                keyword,
                Field.FILE_NAME,
                Field.CONTENT);
        SearchRequestBuilder srb = elasticsearchTemplate
                .getClient()
                .prepareSearch(Index.FILE_FULL_TEXT);
        srb.setQuery(qb);

        //type
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

        //highlight
        srb.addHighlightedField(Field.FILE_NAME);
        srb.addHighlightedField(Field.CONTENT);
//        srb.setHighlighterPhraseLimit(maxContentLength);
        srb.setHighlighterPreTags("<span>");
        srb.setHighlighterPostTags("</span>");
        srb.setHighlighterFragmentSize(maxHighlightedSize);

        //pagination
        PageModel<BaseSearchResult> pageModel = new PageModel<>();
        pageModel.setCurrent(currentPage);
        pageModel.setSize(pageSize);
        Long rowFrom = pageModel.getStart();

        srb.setFrom(rowFrom.intValue());
        srb.setSize(pageSize);

        //action
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

                String owner = (String) searchHit.getSource().get("owner");
                String group = (String) searchHit.getSource().get("group");
                Long timestamp = (Long) searchHit.getSource().get("timestamp");

                String fileName = (String) searchHit.getSource().get("fileName");
                Long size = ((Integer) searchHit.getSource().get("size")).longValue();
                Long lastModified = (Long) searchHit.getSource().get("lastModified");

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
                    if(content.length()> maxHighlightedSize){
                        content = content.substring(0, maxHighlightedSize);
                    }
                }

                result.setOwner(owner);
                result.setGroup(group);
                result.setTimestamp(timestamp);

                result.setFileName(fileName);
                result.setSize(size);
                result.setLastModified(lastModified);
                result.setContent(content);

                resultList.add(result);
            }
        }

        Long count = sr.getHits().totalHits();
        pageModel.setCount(count);
        pageModel.setDatas(resultList);

        return pageModel;
    }

}
