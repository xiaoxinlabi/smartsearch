package info.puton.product.smartsearch.service.impl;

import info.puton.product.smartsearch.constant.*;
import info.puton.product.smartsearch.dao.ElasticSearchDao;
import info.puton.product.smartsearch.model.Address;
import info.puton.product.smartsearch.model.FileFullText;
import info.puton.product.smartsearch.service.AddressIndexer;
import info.puton.product.smartsearch.service.FileIndexer;
import info.puton.product.smartsearch.util.FileUtil;
import org.elasticsearch.action.delete.DeleteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by taoyang on 2016/9/21.
 */
@Service
public class ElasticSearchService implements FileIndexer, AddressIndexer {

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
        String type = fileFullText.getType();
        elasticSearchDao.createDocument(Index.FILE_FULL_TEXT, type, id, data);
    }

    @Override
    public void deleteFile(String type, String id) {
        elasticSearchDao.deleteDocument(Index.FILE_FULL_TEXT, type, id);
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
        data.put("email", address.getEmail());
        data.put("address", address.getAddress());
        data.put("qq", address.getQq());
        data.put("organization", address.getOrganization());
        data.put("department", address.getDepartment());
        data.put("position", address.getPosition());
        data.put("remark", address.getRemark());

        data.put("owner", address.getOwner()!=null ? address.getOwner() : Owner.DEFAULT);
        data.put("group", address.getGroup()!=null ? address.getGroup() : Group.DEFAULT);
        data.put("timestamp", address.getTimestamp()!=null ? address.getTimestamp():System.currentTimeMillis());
        elasticSearchDao.createDocument(Index.ADDRESS, Type.ADDRESS, id, data);
    }

    @Override
    public void deleteAddress(String type, String id) {
        elasticSearchDao.deleteDocument(Index.ADDRESS, type, id);
    }
}
