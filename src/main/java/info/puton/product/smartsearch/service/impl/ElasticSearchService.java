package info.puton.product.smartsearch.service.impl;

import info.puton.product.smartsearch.constant.Analyzer;
import info.puton.product.smartsearch.constant.Index;
import info.puton.product.smartsearch.constant.Type;
import info.puton.product.smartsearch.dao.ElasticSearchDao;
import info.puton.product.smartsearch.model.FileFullText;
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
public class ElasticSearchService implements FileIndexer {

    @Autowired
    ElasticSearchDao elasticSearchDao;

    @Override
    public void add(FileFullText fileFullText) {
        String id = fileFullText.getId();
        Map data = new HashMap();
        data.put("fileName", fileFullText.getFileName());
        data.put("author", fileFullText.getAuthor());
        data.put("modifyDate", fileFullText.getModifyDate());
        data.put("content", fileFullText.getContent());
        String type = FileUtil.getFileSuffix(fileFullText.getFileName());
        elasticSearchDao.createDocument(Index.FILE_FULL_TEXT, type, id, data);
    }

    @Override
    public void delete(String index, String type, String id) {
        elasticSearchDao.deleteDocument(index, type, id);
    }


    @Override
    public void init() {
        try{
            elasticSearchDao.deleteIndex(Index.FILE_FULL_TEXT);
        } catch (Exception e){
            e.printStackTrace();
        }
        elasticSearchDao.createIndex(Index.FILE_FULL_TEXT);
        String docSource =
                "{\n" +
                "  \"properties\": {\n" +
                "    \"fileName\": {\n" +
                "      \"type\": \"string\",\n" +
                "      \"analyzer\": \"" + Analyzer.IK + "\"\n" +
                "    },\n" +
                "    \"author\": {\n" +
                "      \"type\": \"string\",\n" +
                "      \"analyzer\": \"" + Analyzer.IK + "\"\n" +
                "    },\n" +
                "    \"content\": {\n" +
                "      \"type\": \"string\",\n" +
                "      \"analyzer\": \"" + Analyzer.IK + "\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        elasticSearchDao.createSchema(Index.FILE_FULL_TEXT, Type.DOC, docSource);
        String docxSource =
                "{\n" +
                "  \"properties\": {\n" +
                "    \"fileName\": {\n" +
                "      \"type\": \"string\",\n" +
                "      \"analyzer\": \"" + Analyzer.IK + "\"\n" +
                "    },\n" +
                "    \"author\": {\n" +
                "      \"type\": \"string\",\n" +
                "      \"analyzer\": \"" + Analyzer.IK + "\"\n" +
                "    },\n" +
                "    \"content\": {\n" +
                "      \"type\": \"string\",\n" +
                "      \"analyzer\": \"" + Analyzer.IK + "\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        elasticSearchDao.createSchema(Index.FILE_FULL_TEXT, Type.DOCX, docxSource);
    }

}
