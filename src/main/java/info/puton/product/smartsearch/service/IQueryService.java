package info.puton.product.smartsearch.service;

import info.puton.product.smartsearch.model.QueryResult;

/**
 * Created by taoyang on 11/6/15.
 */
public interface IQueryService {

    QueryResult queryResult(String keyword, String type, Integer currentPage, Integer pageSize);

}
