package info.puton.product.smartsearch.service.impl;

import com.google.gson.Gson;
import info.puton.product.smartsearch.dao.SmartSearchDao;
import info.puton.product.smartsearch.model.BaseSearchResult;
import info.puton.product.smartsearch.model.QueryResult;
import info.puton.product.smartsearch.service.IQueryService;
import info.puton.product.smartsearch.service.SmartSearchHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taoyang on 11/6/15.
 */
@Service
public class QueryServiceImpl implements IQueryService {

    @Autowired
    SmartSearchDao smartSearchDao;

    public QueryResult queryResult(String keyword, String type, Integer currentPage, Integer pageSize) {

        //params
        Map params = new HashMap();
        params.put("keyword", keyword);
        params.put("type", type);

        //TODO need support for type / pagination
//        resultMap.put("file",smartSearchDao.highlightQuery(keyword, filterMap, currentPage, pageSize));
        List<BaseSearchResult> results = smartSearchDao.highlightQuery(params);

        return new QueryResult(results);
    }

}
