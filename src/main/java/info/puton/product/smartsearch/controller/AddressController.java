package info.puton.product.smartsearch.controller;

import info.puton.product.smartsearch.model.ActionResult;
import info.puton.product.smartsearch.model.Address;
import info.puton.product.smartsearch.service.AddressIndexer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    AddressIndexer addressIndexer;

    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Map addAdress(
              @RequestParam(value = "accountId", required = false) String accountId
            , @RequestParam(value = "englishName", required = false) String englishName
            , @RequestParam(value = "chineseName", required = false) String chineseName
            , @RequestParam(value = "fixedPhone", required = false) String fixedPhone
            , @RequestParam(value = "mobilePhone", required = false) String mobilePhone
            , @RequestParam(value = "email", required = false) String email
            , @RequestParam(value = "address", required = false) String address
            , @RequestParam(value = "qq", required = false) String qq
            , @RequestParam(value = "organizetion", required = false) String organizetion
            , @RequestParam(value = "department", required = false) String department
            , @RequestParam(value = "position", required = false) String position
            , @RequestParam(value = "remark", required = false) String remark
    ){
        Map result =new HashMap();
        try{
            Address addressModel = new Address();
            addressModel.setAccountId(accountId);
            addressModel.setAccountId(accountId);
            addressModel.setEnglishName(englishName);
            addressModel.setChineseName(chineseName);
            addressModel.setFixedPhone(fixedPhone);
            addressModel.setMobilePhone(mobilePhone);
            addressModel.setEmail(email);
            addressModel.setAddress(address);
            addressModel.setQq(qq);
            addressModel.setOrganization(organizetion);
            addressModel.setDepartment(department);
            addressModel.setPosition(position);
            addressModel.setRemark(remark);
            addressIndexer.addAddress(addressModel);
            result.put("status","success");
            result.put("detail",addressModel);
        }catch (Exception e){
            result.put("status","error");
            e.printStackTrace();
        }finally {
            return result;
        }

    }
}
