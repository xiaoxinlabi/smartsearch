package info.puton.product.smartsearch.service.impl;

import info.puton.product.smartsearch.constant.*;
import info.puton.product.smartsearch.dao.ElasticSearchDao;
import info.puton.product.smartsearch.model.Address;
import info.puton.product.smartsearch.model.FileFullText;
import info.puton.product.smartsearch.model.Website;
import info.puton.product.smartsearch.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by taoyang on 2016/9/21.
 */
@Service
public class ElasticSearchService implements BaseIndexer, FileIndexer, AddressIndexer, WebsiteIndexer, RdbmsIndexer {

    @Autowired
    ElasticSearchDao elasticSearchDao;

    @Override
    public void initFile() {
        try{
            elasticSearchDao.deleteIndex(Index.FILE_FULL_TEXT);
        } catch (Exception e){
            e.printStackTrace();
        }
        elasticSearchDao.createIndex(Index.FILE_FULL_TEXT);
        //txt
        String txtSource =
                        "{\n" +
                        "  \"properties\": {\n" +
                        "    \"fileName\": {\n" +
                        "      \"type\": \"string\",\n" +
                        "      \"analyzer\": \"" + Analyzer.IK_SMART + "\"\n" +
                        "    },\n" +
                        "    \"content\": {\n" +
                        "      \"type\": \"string\",\n" +
                        "      \"analyzer\": \"" + Analyzer.IK_SMART + "\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}";
        elasticSearchDao.createSchema(Index.FILE_FULL_TEXT, Type.TXT, txtSource);
        //doc
        String docSource =
                        "{\n" +
                        "  \"properties\": {\n" +
                        "    \"fileName\": {\n" +
                        "      \"type\": \"string\",\n" +
                        "      \"analyzer\": \"" + Analyzer.IK_SMART + "\"\n" +
                        "    },\n" +
                        "    \"content\": {\n" +
                        "      \"type\": \"string\",\n" +
                        "      \"analyzer\": \"" + Analyzer.IK_SMART + "\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}";
        elasticSearchDao.createSchema(Index.FILE_FULL_TEXT, Type.DOC, docSource);
        //docx
        String docxSource =
                        "{\n" +
                        "  \"properties\": {\n" +
                        "    \"fileName\": {\n" +
                        "      \"type\": \"string\",\n" +
                        "      \"analyzer\": \"" + Analyzer.IK_SMART + "\"\n" +
                        "    },\n" +
                        "    \"content\": {\n" +
                        "      \"type\": \"string\",\n" +
                        "      \"analyzer\": \"" + Analyzer.IK_SMART + "\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}";
        elasticSearchDao.createSchema(Index.FILE_FULL_TEXT, Type.DOCX, docxSource);
        //xls
        String xlsSource =
                        "{\n" +
                        "  \"properties\": {\n" +
                        "    \"fileName\": {\n" +
                        "      \"type\": \"string\",\n" +
                        "      \"analyzer\": \"" + Analyzer.IK_SMART + "\"\n" +
                        "    },\n" +
                        "    \"content\": {\n" +
                        "      \"type\": \"string\",\n" +
                        "      \"analyzer\": \"" + Analyzer.IK_SMART + "\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}";
        elasticSearchDao.createSchema(Index.FILE_FULL_TEXT, Type.XLS, xlsSource);
        //xlsx
        String xlsxSource =
                        "{\n" +
                        "  \"properties\": {\n" +
                        "    \"fileName\": {\n" +
                        "      \"type\": \"string\",\n" +
                        "      \"analyzer\": \"" + Analyzer.IK_SMART + "\"\n" +
                        "    },\n" +
                        "    \"content\": {\n" +
                        "      \"type\": \"string\",\n" +
                        "      \"analyzer\": \"" + Analyzer.IK_SMART + "\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}";
        elasticSearchDao.createSchema(Index.FILE_FULL_TEXT, Type.XLSX, xlsxSource);
        //ppt
        String pptSource =
                        "{\n" +
                        "  \"properties\": {\n" +
                        "    \"fileName\": {\n" +
                        "      \"type\": \"string\",\n" +
                        "      \"analyzer\": \"" + Analyzer.IK_SMART + "\"\n" +
                        "    },\n" +
                        "    \"content\": {\n" +
                        "      \"type\": \"string\",\n" +
                        "      \"analyzer\": \"" + Analyzer.IK_SMART + "\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}";
        elasticSearchDao.createSchema(Index.FILE_FULL_TEXT, Type.PPT, pptSource);
        //pptx
        String pptxSource =
                        "{\n" +
                        "  \"properties\": {\n" +
                        "    \"fileName\": {\n" +
                        "      \"type\": \"string\",\n" +
                        "      \"analyzer\": \"" + Analyzer.IK_SMART + "\"\n" +
                        "    },\n" +
                        "    \"content\": {\n" +
                        "      \"type\": \"string\",\n" +
                        "      \"analyzer\": \"" + Analyzer.IK_SMART + "\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}";
        elasticSearchDao.createSchema(Index.FILE_FULL_TEXT, Type.PPTX, pptxSource);
        //pdf
        String pdfSource =
                        "{\n" +
                        "  \"properties\": {\n" +
                        "    \"fileName\": {\n" +
                        "      \"type\": \"string\",\n" +
                        "      \"analyzer\": \"" + Analyzer.IK_SMART + "\"\n" +
                        "    },\n" +
                        "    \"content\": {\n" +
                        "      \"type\": \"string\",\n" +
                        "      \"analyzer\": \"" + Analyzer.IK_SMART + "\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}";
        elasticSearchDao.createSchema(Index.FILE_FULL_TEXT, Type.PDF, pdfSource);
    }

    @Override
    public void addFile(FileFullText fileFullText) {
        String id = fileFullText.getId();
        Map data = new HashMap();
        data.put("fileName", fileFullText.getFileName());
        data.put("size", fileFullText.getSize());
        data.put("lastModified", fileFullText.getLastModified());
        data.put("content", fileFullText.getContent());
        data.put("owner", fileFullText.getOwner()!=null ? fileFullText.getOwner() : Owner.DEFAULT);
        data.put("group", fileFullText.getGroup()!=null ? fileFullText.getGroup() : Group.DEFAULT);
        data.put("timestamp", fileFullText.getTimestamp()!=null ? fileFullText.getTimestamp():System.currentTimeMillis());
        data.put("origin", fileFullText.getOrigin()!=null ? fileFullText.getOrigin() : Origin.DEFAULT);
        String type = fileFullText.getType();
        elasticSearchDao.createDocument(Index.FILE_FULL_TEXT, type, id, data);
    }

    @Override
    public void initAddress() {
        try{
            elasticSearchDao.deleteIndex(Index.ADDRESS);
        } catch (Exception e){
            e.printStackTrace();
        }
        elasticSearchDao.createIndex(Index.ADDRESS);
        //txt
        String addressSource =
                        "{\n" +
                        "  \"properties\": {\n" +
                        "    \"chineseName\": {\n" +
                        "      \"type\": \"string\",\n" +
                        "      \"analyzer\": \"" + Analyzer.IK + "\"\n" +
                        "    }" +
                        "  }\n" +
                        "}";
        elasticSearchDao.createSchema(Index.ADDRESS, Type.ADDRESS, addressSource);
    }

    @Override
    public void addAddress(Address address) {
        String id = address.getId();
        Map data = new HashMap();
        data.put("accountId", address.getAccountId());
        data.put("englishName", address.getEnglishName());
        data.put("chineseName", address.getChineseName());
        data.put("fixedPhone", address.getFixedPhone());
        data.put("mobilePhone", address.getMobilePhone());
        data.put("fax", address.getFax());
        data.put("email", address.getEmail());
        data.put("address", address.getAddress());
        data.put("qq", address.getQq());
        data.put("organization", address.getOrganization());
        data.put("department", address.getDepartment());
        data.put("position", address.getPosition());
        data.put("remark", address.getRemark());

        data.put("owner", address.getOwner()!=null ? address.getOwner() : Owner.DEFAULT);
        data.put("group", address.getGroup()!=null ? address.getGroup() : Group.PUBLIC);
        data.put("timestamp", address.getTimestamp()!=null ? address.getTimestamp():System.currentTimeMillis());
        elasticSearchDao.createDocument(Index.ADDRESS, Type.ADDRESS, id, data);
    }

    @Override
    public void initWebsite() {
        try{
            elasticSearchDao.deleteIndex(Index.WEBSITE);
        } catch (Exception e){
            e.printStackTrace();
        }
        elasticSearchDao.createIndex(Index.WEBSITE);
        //website
        String websiteSource =
                        "{\n" +
                        "  \"properties\": {\n" +
                        "    \"title\": {\n" +
                        "      \"type\": \"string\",\n" +
                        "      \"analyzer\": \"" + Analyzer.IK_SMART + "\"\n" +
                        "    },\n" +
                        "    \"keywords\": {\n" +
                        "      \"type\": \"string\",\n" +
                        "      \"analyzer\": \"" + Analyzer.IK_SMART + "\"\n" +
                        "    },\n" +
                        "    \"content\": {\n" +
                        "      \"type\": \"string\",\n" +
                        "      \"analyzer\": \"" + Analyzer.IK_SMART + "\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}";
        elasticSearchDao.createSchema(Index.WEBSITE, Type.WEBSITE, websiteSource);
    }

    @Override
    public void addWebsite(Website website) {
        String id = website.getId();
        System.out.println("id:" + id);

        Map data = new HashMap();
        data.put("url", website.getUrl());
        data.put("title", website.getTitle());
        data.put("keywords", website.getKeywords());
        data.put("description", website.getDescription());
        data.put("content", website.getContent());

        data.put("owner", website.getOwner()!=null ? website.getOwner() : Owner.DEFAULT);
        data.put("group", website.getGroup()!=null ? website.getGroup() : Group.PUBLIC);
        data.put("timestamp", website.getTimestamp()!=null ? website.getTimestamp():System.currentTimeMillis());
        elasticSearchDao.createDocument(Index.WEBSITE, Type.WEBSITE, id, data);
    }

    @Override
    public void deleteIndex(String index) {
        elasticSearchDao.deleteIndex(index);
    }

    @Override
    public void deleteDocument(String index, String type, String id) {
        elasticSearchDao.deleteDocument(index, type, id);
    }

    @Override
    public Map getDocument(String index, String type, String id) {
        return elasticSearchDao.getDocument(index, type, id);
    }

    @Override
    public void initRdbms() {
        try{
            elasticSearchDao.deleteIndex(Index.RDBMS);
        } catch (Exception e){
            e.printStackTrace();
        }
        elasticSearchDao.createIndex(Index.RDBMS);
        //law
        String lawSource =
                        "{\n" +
                        "  \"properties\": {\n" +
                        "    \"lawname\": {\n" +
                        "      \"type\": \"string\",\n" +
                        "      \"analyzer\": \"" + Analyzer.IK_SMART + "\"\n" +
                        "    },\n" +
                        "    \"itemtitle\": {\n" +
                        "      \"type\": \"string\",\n" +
                        "      \"analyzer\": \"" + Analyzer.IK_SMART + "\"\n" +
                        "    },\n" +
                        "    \"itemcontent\": {\n" +
                        "      \"type\": \"string\",\n" +
                        "      \"analyzer\": \"" + Analyzer.IK_SMART + "\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}";
        elasticSearchDao.createSchema(Index.RDBMS, Type.LAW, lawSource);
    }
}
