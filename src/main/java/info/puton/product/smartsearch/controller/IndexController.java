package info.puton.product.smartsearch.controller;

import info.puton.product.smartsearch.constant.Field;
import info.puton.product.smartsearch.constant.Group;
import info.puton.product.smartsearch.constant.Index;
import info.puton.product.smartsearch.model.ActionResult;
import info.puton.product.smartsearch.service.BaseIndexer;
import info.puton.product.smartsearch.service.FileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by taoyang on 11/5/15.
 */

@Controller
@RequestMapping(value="/index")
public class IndexController {

    @Autowired
    BaseIndexer baseIndexer;

    @Autowired
    FileStorage fileStorage;

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ActionResult delete(
            @RequestParam(value="index",defaultValue="") String index
            , @RequestParam(value="type",defaultValue="") String type
            , @RequestParam(value="id",defaultValue="") String id){
        baseIndexer.deleteDocument(index, type, id);
        if (index.equals(Index.FILE_FULL_TEXT)){
            try {
                fileStorage.deleteFile(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ActionResult(true);
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ActionResult get(
            @RequestParam(value="index",defaultValue="") String index
            , @RequestParam(value="type",defaultValue="") String type
            , @RequestParam(value="id",defaultValue="") String id){
        Map source= baseIndexer.getDocument(index, type, id);
        return new ActionResult(source);
    }

    @ResponseBody
    @RequestMapping(value = "/share", method = RequestMethod.POST)
    public ActionResult share(
            @RequestParam(value="index",defaultValue="") String index
            , @RequestParam(value="type",defaultValue="") String type
            , @RequestParam(value="id",defaultValue="") String id){
        Map fields = new HashMap();
        fields.put(Field.GROUP, Group.PUBLIC);
        baseIndexer.updateDocument(index, type, id, fields);
        return new ActionResult(true);
    }

    @ResponseBody
    @RequestMapping(value = "/noshare", method = RequestMethod.POST)
    public ActionResult noShare(
            @RequestParam(value="index",defaultValue="") String index
            , @RequestParam(value="type",defaultValue="") String type
            , @RequestParam(value="id",defaultValue="") String id){
        Map fields = new HashMap();
        fields.put(Field.GROUP, Group.DEFAULT);
        baseIndexer.updateDocument(index, type, id, fields);
        return new ActionResult(true);
    }

}
