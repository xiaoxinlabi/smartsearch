package info.puton.product.smartsearch.service.impl;

import info.puton.product.smartsearch.constant.Index;
import info.puton.product.smartsearch.constant.Type;
import info.puton.product.smartsearch.dao.ElasticSearchDao;
import info.puton.product.smartsearch.model.FileFullText;
import info.puton.product.smartsearch.service.FileIndexer;
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
    public void createIndex(FileFullText fileFullText) {

        String id = fileFullText.getId();
        Map data = new HashMap();
        data.put("fileName", fileFullText.getFileName());
        data.put("author", fileFullText.getAuthor());
        data.put("modifyDate", fileFullText.getModifyDate());
        data.put("content", fileFullText.getContent());

        elasticSearchDao.createIndex(Index.SMART_SEARCH, Type.FILE_FULL_TEXT, id, data);

    }
}
