package info.puton.product.smartsearch.controller;

import info.puton.product.smartsearch.model.ActionResult;
import info.puton.product.smartsearch.service.IQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by taoyang on 11/5/15.
 */

@RestController
public class QueryController {

    @Autowired
    IQueryService qs;

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public ActionResult query(@RequestParam(value="keyword",defaultValue="") String keyword
            ,@RequestParam(value="type",defaultValue="") String type
            ,@RequestParam(value="currentPage",defaultValue= "1") Integer currentPage
            ,@RequestParam(value="pageSize",defaultValue="10") Integer pageSize){
        return new ActionResult(qs.queryResult(keyword, type, currentPage, pageSize));
    }

}
