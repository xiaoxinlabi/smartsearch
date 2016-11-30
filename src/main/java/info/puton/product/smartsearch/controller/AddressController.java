package info.puton.product.smartsearch.controller;

import info.puton.product.smartsearch.constant.FilePath;
import info.puton.product.smartsearch.constant.Group;
import info.puton.product.smartsearch.constant.Origin;
import info.puton.product.smartsearch.model.ActionResult;
import info.puton.product.smartsearch.model.Address;
import info.puton.product.smartsearch.service.AddressHandler;
import info.puton.product.smartsearch.service.AddressIndexer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Pauline on 16/10/13.
 */

@Controller
@RequestMapping(value="/address")
public class AddressController {

    @Autowired
    AddressHandler addressHandler;

    @Autowired
    AddressIndexer addressIndexer;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String addAddress(
              @RequestParam(value = "accountId", required = false) String accountId
            , @RequestParam(value = "englishName", required = false) String englishName
            , @RequestParam(value = "chineseName", required = false) String chineseName
            , @RequestParam(value = "fixedPhone", required = false) String fixedPhone
            , @RequestParam(value = "mobilePhone", required = false) String mobilePhone
            , @RequestParam(value = "fax", required = false) String fax
            , @RequestParam(value = "email", required = false) String email
            , @RequestParam(value = "address", required = false) String address
            , @RequestParam(value = "qq", required = false) String qq
            , @RequestParam(value = "organizetion", required = false) String organizetion
            , @RequestParam(value = "department", required = false) String department
            , @RequestParam(value = "position", required = false) String position
            , @RequestParam(value = "remark", required = false) String remark
    ){
        try{
            Address addressModel = new Address();
            addressModel.setAccountId(accountId);
            addressModel.setAccountId(accountId);
            addressModel.setEnglishName(englishName);
            addressModel.setChineseName(chineseName);
            addressModel.setFixedPhone(fixedPhone);
            addressModel.setMobilePhone(mobilePhone);
            addressModel.setFax(fax);
            addressModel.setEmail(email);
            addressModel.setAddress(address);
            addressModel.setQq(qq);
            addressModel.setOrganization(organizetion);
            addressModel.setDepartment(department);
            addressModel.setPosition(position);
            addressModel.setRemark(remark);
            addressIndexer.addAddress(addressModel);
            return "redirect:/admin.html?status=ok";
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/admin.html?status=error";
        }

    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam(value = "file") MultipartFile file,
                         HttpServletRequest request) {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        // new Date()为获取当前系统时间
        String timestamp = df.format(new Date());
//        System.out.println(timestamp);
        String filePath = FilePath.UPLOAD + timestamp;
        //创建你要保存的文件的路径
//        String path = request.getSession().getServletContext().getRealPath("uploadfile");
        String path = request.getSession().getServletContext().getRealPath(filePath);
        //获取该文件的文件名
        String fileName = file.getOriginalFilename();

//        System.out.println(path);
//        System.out.println(fileName);
        File targetFile = new File(path, fileName);
        System.out.println(targetFile);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        // 临时保存
        try {
            file.transferTo(targetFile);
            //文件处理
            Map additional = new HashMap();
            addressHandler.handleFile(targetFile.getPath(), additional);
            targetFile.delete();
            return "redirect:/admin.html?status=ok";
        } catch (Exception e) {
            e.printStackTrace();
            targetFile.delete();
            return "redirect:/admin.html?status=error";
        }
    }

    @ResponseBody
    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public ActionResult init(){
        addressIndexer.initAddress();
        return new ActionResult(true);
    }

}
