package info.puton.product.smartsearch.controller;

import info.puton.product.smartsearch.model.ActionResult;
import info.puton.product.smartsearch.model.Website;
import info.puton.product.smartsearch.service.WebsiteIndexer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pauline on 16/10/13.
 */
@Controller
@RequestMapping(value="/website")
public class WebsiteController {
    @Autowired
    WebsiteIndexer websiteIndexer;

    @RequestMapping(value="/add",method = RequestMethod.POST)
    public String add(
             @RequestParam(value="url",required = false)String url
            ,@RequestParam(value="title",required = false)String title
            ,@RequestParam(value="keywords",required = false)String keywords
            ,@RequestParam(value="description",required = false)String description
            ,@RequestParam(value="content",required = false)String content
    ){
        try{
            Website websiteModel = new Website();
            websiteModel.setUrl(url);
            websiteModel.setTitle(title);
            websiteModel.setKeywords(keywords);
            websiteModel.setDescription(description);
            websiteModel.setContent(content);
            websiteIndexer.addWebsite(websiteModel);
            return "redirect:/admin.jsp?status=ok";
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/admin.jsp?status=error";
        }
    }

    @ResponseBody
    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public ActionResult init(){
        websiteIndexer.initWebsite();
        return new ActionResult(true);
    }

}
