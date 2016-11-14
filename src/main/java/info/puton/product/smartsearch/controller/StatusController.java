package info.puton.product.smartsearch.controller;

import info.puton.product.smartsearch.constant.Status;
import info.puton.product.smartsearch.model.StatusResult;
import info.puton.product.smartsearch.service.IQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by taoyang on 2016/11/14.
 */
@Controller
@RequestMapping(value="/status")
public class StatusController {

    @Autowired
    IQueryService qs;

    @ResponseBody
    @RequestMapping(value="/search")
    public StatusResult search(){
        StatusResult statusResult = new StatusResult();
        try{
            qs.queryResult("银行", null, 1, 10);
            statusResult.setStatus(Status.OK);
            statusResult.setDescription("智搜查询服务正常");
        } catch (Exception e){
            e.printStackTrace();
            statusResult.setStatus(Status.ERROR);
            statusResult.setDescription("智搜查询服务异常："+e.getLocalizedMessage());
        }
        return statusResult;
    }

}
