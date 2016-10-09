package info.puton.product.smartsearch.service;

import info.puton.product.smartsearch.model.BaseSearchResult;
import info.puton.product.smartsearch.model.PageModel;

/**
 * Created by taoyang on 11/6/15.
 */
public interface IQueryService {

    PageModel<BaseSearchResult> queryResult(String keyword, String type, Integer currentPage, Integer pageSize);

}
