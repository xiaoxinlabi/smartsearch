package info.puton.product.smartsearch.controller;

import info.puton.product.smartsearch.constant.FilePath;
import info.puton.product.smartsearch.model.ActionResult;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * Created by Pauline on 16/10/13.
 */
@Controller
@RequestMapping(value="/cache")
public class CacheController {

    @ResponseBody
    @RequestMapping(value = "/ini", method = RequestMethod.DELETE)
    public ActionResult deleteIniCache(HttpServletRequest request){
        String filePath = FilePath.CACHE.INITIAL;
        //创建你要保存的文件的路径
        String path = request.getSession().getServletContext().getRealPath(filePath);
        try {
            FileUtils.deleteDirectory(new File(path));
            return new ActionResult(true);
        } catch (IOException e) {
            e.printStackTrace();
            return new ActionResult(false);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/pdf", method = RequestMethod.DELETE)
    public ActionResult deletePdfCache(HttpServletRequest request){
        String filePath = FilePath.CACHE.PDF;
        //创建你要保存的文件的路径
        String path = request.getSession().getServletContext().getRealPath(filePath);
        try {
            FileUtils.deleteDirectory(new File(path));
            return new ActionResult(true);
        } catch (IOException e) {
            e.printStackTrace();
            return new ActionResult(false);
        }
    }

}
