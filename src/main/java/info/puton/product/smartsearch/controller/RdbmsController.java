package info.puton.product.smartsearch.controller;

import info.puton.product.smartsearch.model.ActionResult;
import info.puton.product.smartsearch.service.RdbmsIndexer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pauline on 16/10/13.
 */

@Controller
@RequestMapping(value="/rdbms")
public class RdbmsController {
    @Autowired
    RdbmsIndexer rdbmsIndexer;

    @ResponseBody
    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public ActionResult init(){
        rdbmsIndexer.initRdbms();
        return new ActionResult(true);
    }

}
