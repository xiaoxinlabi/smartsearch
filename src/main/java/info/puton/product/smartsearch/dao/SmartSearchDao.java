package info.puton.product.smartsearch.dao;

import info.puton.product.smartsearch.constant.*;
import info.puton.product.smartsearch.model.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
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

    private String[] allIndexTypes = {Index.FILE_FULL_TEXT, Index.ADDRESS, Index.WEBSITE, Index.RDBMS};

    private String[] allFileTypes = {Type.DOC, Type.DOCX, Type.XLS, Type.XLSX, Type.PPT, Type.PPTX, Type.PDF, Type.TXT, Type.GD, Type.GW};

    public PageModel<BaseSearchResult> queryResult(Map params){

        String keyword = (String) params.get("keyword");
        String filterType = (String) params.get("type");
        Integer currentPage = (Integer) params.get("currentPage");
        Integer pageSize = (Integer) params.get("pageSize");

        //query
        QueryBuilder qbRelative = QueryBuilders.multiMatchQuery(
                keyword,
                Field.ORIGIN,
                Field.FILE_NAME,
                Field.CONTENT,
                Field.ENGLISH_NAME,
                Field.CHINESE_NAME,
                Field.MOBILE_PHONE,
                Field.FIXED_PHONE,
                Field.FAX,
                Field.TITLE,
                Field.KEYWORDS,
                Field.DESCRIPTION,
                Field.LAW_NAME,
                Field.ITEM_TITLE,
                Field.ITEM_CONTENT);

        QueryBuilder qbPublic = QueryBuilders.matchPhraseQuery(Field.GROUP, Group.PUBLIC);
        BoolQueryBuilder bqb = QueryBuilders.boolQuery().must(qbRelative);
        //auth
        BoolQueryBuilder bqb_auth =  QueryBuilders.boolQuery().should(qbPublic);
        String permissions = "read;";
        Subject currentUser = SecurityUtils.getSubject();
        if(currentUser.isAuthenticated() || currentUser.isRemembered()){
            if(currentUser.getPrincipal().toString().equals("admin")){
                //admin
                permissions+="write;";
            }else{
                //user
                QueryBuilder qbUser = QueryBuilders.matchPhraseQuery(Field.OWNER, currentUser.getPrincipal().toString());
                bqb_auth = bqb_auth.should(qbUser);
                bqb = bqb.must(bqb_auth);
            }
            //myfile
            if(filterType.equals(Type.MY_FILE)){
                QueryBuilder me = QueryBuilders.matchPhraseQuery(Field.OWNER, currentUser.getPrincipal().toString());
                QueryBuilder upload = QueryBuilders.matchPhraseQuery(Field.ORIGIN, Origin.UPLOAD);
                bqb=QueryBuilders.boolQuery().must(me).must(upload);
            }
        } else{
            //guest
            bqb = bqb.must(bqb_auth);
        }

        QueryBuilder qb = bqb;

        SearchRequestBuilder srb = elasticsearchTemplate
                .getClient()
                .prepareSearch(allIndexTypes);

        //type
        if(filterType!=null && !"".equals(filterType)){
            if(filterType.equals(Type.FILE) || filterType.equals(Type.MY_FILE)){
                srb = elasticsearchTemplate.getClient().prepareSearch(Index.FILE_FULL_TEXT);
            } else if(filterType.equals(Type.DOC)){
                srb.setTypes(Type.DOC, Type.DOCX);
            } else if(filterType.equals(Type.XLS)){
                srb.setTypes(Type.XLS, Type.XLSX);
            } else if(filterType.equals(Type.PPT)){
                srb.setTypes(Type.PPT, Type.PPTX);
            } else if(filterType.equals(Type.GD)){
                srb.setTypes(Type.GD, Type.GW);
            } else {
                srb.setTypes(filterType);
            }
        }

        srb.setQuery(qb);

        //highlight
        srb.addHighlightedField(Field.ORIGIN);
        srb.addHighlightedField(Field.FILE_NAME);
        srb.addHighlightedField(Field.CONTENT);
        srb.addHighlightedField(Field.ENGLISH_NAME);
        srb.addHighlightedField(Field.CHINESE_NAME);
        srb.addHighlightedField(Field.MOBILE_PHONE);
        srb.addHighlightedField(Field.FIXED_PHONE);
        srb.addHighlightedField(Field.FAX);
        srb.addHighlightedField(Field.TITLE);
        srb.addHighlightedField(Field.KEYWORDS);
        srb.addHighlightedField(Field.DESCRIPTION);
        srb.addHighlightedField(Field.LAW_NAME);
        srb.addHighlightedField(Field.ITEM_TITLE);
        srb.addHighlightedField(Field.ITEM_CONTENT);
//        srb.setHighlighterPhraseLimit(maxContentLength);
        srb.setHighlighterPreTags("<span class=\"ss-highlight\">");
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
            String owner = (String) searchHit.getSource().get("owner");
            String group = (String) searchHit.getSource().get("group");
            Long timestamp = (Long) searchHit.getSource().get("timestamp");

            String origin = (String) searchHit.getSource().get("origin");
            Map<String, HighlightField> highlightFields = searchHit.highlightFields();

            if(highlightFields.containsKey("origin")){
                Text[] highlights = highlightFields.get("origin").getFragments();
                origin = highlights[0].toString();
            }

            if(index.equals(Index.FILE_FULL_TEXT)){

                FileFullText result = new FileFullText();

                String fileName = (String) searchHit.getSource().get("fileName");
                Long size = ((Integer) searchHit.getSource().get("size")).longValue();
                Long lastModified = (Long) searchHit.getSource().get("lastModified");

                String content = (String) searchHit.getSource().get("content");
                if(highlightFields.containsKey("fileName")){
                    Text[] highlights = highlightFields.get("fileName").getFragments();
                    fileName = highlights[0].toString();
                }
                if(highlightFields.containsKey("content")){
                    Text[] highlights = highlightFields.get("content").getFragments();
                    content = highlights[0].toString();
                } else {
                    if(content.length()> maxHighlightedSize){
                        content = content.substring(0, maxHighlightedSize);
                    }
                }

                result.setIndex(index);
                result.setType(type);
                result.setId(id);
                result.setScore(score);
                result.setOwner(owner);
                result.setGroup(group);
                result.setTimestamp(timestamp);
                result.setOrigin(origin);

                result.setFileName(fileName);
                result.setSize(size);
                result.setLastModified(lastModified);
                result.setContent(content);

                //auth
                result.setPermissions(permissions);

                resultList.add(result);
            } else if(index.equals(Index.ADDRESS)) {

                Address result = new Address();

                String accountId = (String) searchHit.getSource().get("accountId");
                String englishName = (String) searchHit.getSource().get("englishName");
                String chineseName = (String) searchHit.getSource().get("chineseName");
                String fixedPhone = (String) searchHit.getSource().get("fixedPhone");
                String mobilePhone = (String) searchHit.getSource().get("mobilePhone");
                String fax = (String) searchHit.getSource().get("fax");
                String email = (String) searchHit.getSource().get("email");
                String address = (String) searchHit.getSource().get("address");
                String qq = (String) searchHit.getSource().get("qq");
                String organization = (String) searchHit.getSource().get("organization");
                String department = (String) searchHit.getSource().get("department");
                String position = (String) searchHit.getSource().get("position");
                String remark = (String) searchHit.getSource().get("remark");

                if(highlightFields.containsKey("englishName")){
                    Text[] highlights = highlightFields.get("englishName").getFragments();
                    englishName = highlights[0].toString();
                }
                if(highlightFields.containsKey("chineseName")){
                    Text[] highlights = highlightFields.get("chineseName").getFragments();
                    chineseName = highlights[0].toString();
                }
                if(highlightFields.containsKey("mobilePhone")){
                    Text[] highlights = highlightFields.get("mobilePhone").getFragments();
                    mobilePhone = highlights[0].toString();
                }
                if(highlightFields.containsKey("fixedPhone")){
                    Text[] highlights = highlightFields.get("fixedPhone").getFragments();
                    fixedPhone = highlights[0].toString();
                }
                if(highlightFields.containsKey("fax")){
                    Text[] highlights = highlightFields.get("fax").getFragments();
                    fax = highlights[0].toString();
                }

                result.setIndex(index);
                result.setType(type);
                result.setId(id);
                result.setScore(score);
                result.setOwner(owner);
                result.setGroup(group);
                result.setTimestamp(timestamp);
                result.setOrigin(origin);

                result.setAccountId(accountId);
                result.setEnglishName(englishName);
                result.setChineseName(chineseName);
                result.setFixedPhone(fixedPhone);
                result.setMobilePhone(mobilePhone);
                result.setFax(fax);
                result.setEmail(email);
                result.setAddress(address);
                result.setQq(qq);
                result.setOrganization(organization);
                result.setDepartment(department);
                result.setPosition(position);
                result.setRemark(remark);

                //auth
                result.setPermissions(permissions);

                resultList.add(result);

            } else if (index.equals(Index.WEBSITE)) {

                Website result = new Website();

                String url = (String) searchHit.getSource().get("url");
                String title = (String) searchHit.getSource().get("title");
                String keywords = (String) searchHit.getSource().get("keywords");
                String description = (String) searchHit.getSource().get("description");
                String content = (String) searchHit.getSource().get("content");

                if(highlightFields.containsKey("title")){
                    Text[] highlights = highlightFields.get("title").getFragments();
                    title = highlights[0].toString();
                }
                if(highlightFields.containsKey("keywords")){
                    Text[] highlights = highlightFields.get("keywords").getFragments();
                    keywords = highlights[0].toString();
                }
                if(highlightFields.containsKey("description")){
                    Text[] highlights = highlightFields.get("description").getFragments();
                    description = highlights[0].toString();
                }
                if(highlightFields.containsKey("content")){
                    Text[] highlights = highlightFields.get("content").getFragments();
                    content = highlights[0].toString();
                }

                result.setIndex(index);
                result.setType(type);
                result.setId(id);
                result.setScore(score);
                result.setOwner(owner);
                result.setGroup(group);
                result.setTimestamp(timestamp);
                result.setOrigin(origin);

                result.setUrl(url);
                result.setTitle(title);
                result.setKeywords(keywords);
                result.setDescription(description);
                result.setContent(content);

                //auth
                result.setPermissions(permissions);

                resultList.add(result);

            } else if (index.equals(Index.RDBMS)) {

                Law result = new Law();

                String lawname = (String) searchHit.getSource().get("lawname");
                String itemtitle = (String) searchHit.getSource().get("itemtitle");
                String itemcontent = (String) searchHit.getSource().get("itemcontent");
                String content = (String) searchHit.getSource().get("itemcontent");

                if(highlightFields.containsKey("lawname")){
                    Text[] highlights = highlightFields.get("lawname").getFragments();
                    lawname = highlights[0].toString();
                }
                if(highlightFields.containsKey("itemtitle")){
                    Text[] highlights = highlightFields.get("itemtitle").getFragments();
                    itemtitle = highlights[0].toString();
                }
                if(highlightFields.containsKey("itemcontent")){
                    Text[] highlights = highlightFields.get("itemcontent").getFragments();
                    content = highlights[0].toString();
                }

                result.setIndex(index);
                result.setType(type);
                result.setId(id);
                result.setScore(score);
                result.setOwner(owner);
                result.setGroup(group);
                result.setTimestamp(timestamp);
                result.setOrigin(origin);

                result.setId(id);
                result.setLawname(lawname);
                result.setItemtitle(itemtitle);
                result.setItemcontent(itemcontent);
                result.setContent(content);

                //auth
                result.setPermissions(permissions);

                resultList.add(result);

            }

        }

        Long count = sr.getHits().totalHits();
        pageModel.setCount(count);
        pageModel.setDatas(resultList);

        return pageModel;
    }

}
