package info.puton.product.smartsearch.service.impl;

import info.puton.product.smartsearch.dao.SmartSearchDao;
import info.puton.product.smartsearch.model.BaseSearchResult;
import info.puton.product.smartsearch.model.PageModel;
import info.puton.product.smartsearch.model.ActionResult;
import info.puton.product.smartsearch.service.IQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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



    public PageModel<BaseSearchResult> queryResult(String keyword, String type, Integer currentPage, Integer pageSize) {

        //params
        Map params = new HashMap();
        params.put("keyword", keyword);
        params.put("type", type);
        params.put("currentPage", currentPage);
        params.put("pageSize", pageSize);

        //TODO need support for pagination

        PageModel<BaseSearchResult> pageModel = smartSearchDao.queryResult(params);

        return pageModel;
    }

}
