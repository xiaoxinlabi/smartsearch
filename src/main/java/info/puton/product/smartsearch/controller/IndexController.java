package info.puton.product.smartsearch.controller;

import info.puton.product.smartsearch.model.ActionResult;
import info.puton.product.smartsearch.service.BaseIndexer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by taoyang on 11/5/15.
 */

@RestController
@RequestMapping(value="/index")
public class IndexController {

    @Autowired
    BaseIndexer baseIndexer;

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ActionResult delete(
            @RequestParam(value="index",defaultValue="") String index
            , @RequestParam(value="type",defaultValue="") String type
            , @RequestParam(value="id",defaultValue="") String id){
        baseIndexer.deleteDocument(index, type, id);
        return new ActionResult(true);
    }

}
