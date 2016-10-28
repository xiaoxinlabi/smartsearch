package info.puton.product.smartsearch.controller;

import info.puton.product.smartsearch.util.DecriptUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pauline on 16/10/26.
 */

public class LoginController {
    @RequestMapping("/admin.html")
    public ModelAndView getIndex(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("loginsuccess");
        return mav;
    }
//
//    @RequestMapping(value="/otherException.json", method=RequestMethod.POST)
//    @ResponseBody
//    public String otherException(HttpServletRequest request) throws Exception {
//        throw new Exception();
//    }
//
//    //跳转到登录页面
//    @RequestMapping("/login.jhtml")
//    public ModelAndView login() throws Exception {
//        ModelAndView mav = new ModelAndView("login");
//        return mav;
//    }
//
//    //跳转到登录成功页面
//    @RequestMapping("/loginsuccess.jhtml")
//    public ModelAndView loginsuccess() throws Exception {
//        ModelAndView mav = new ModelAndView("loginsuccess");
//        return mav;
//    }
//
//    @RequestMapping("/newPage.jhtml")
//    public ModelAndView newPage() throws Exception {
//        ModelAndView mav = new ModelAndView("newPage");
//        return mav;
//    }
//
//    @RequestMapping("/newPageNotAdd.jhtml")
//    public ModelAndView newPageNotAdd() throws Exception {
//        ModelAndView mav = new ModelAndView("newPageNotAdd");
//        return mav;
//    }
    /**
     * 验证用户名和密码
     * @param String username,String password
     * @return
     */
    @RequestMapping(value = "/login.json")
    @ResponseBody
    public Map checkLogin(
            @RequestParam(value="username", required=false)String username
            ,@RequestParam(value="password" ,required=false)String password
    ){
        Map<String,Object> result = new HashMap<String, Object>();
        try{
            UsernamePasswordToken token = new UsernamePasswordToken(username, DecriptUtil.MD5(password));
            org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
            if(!currentUser.isAuthenticated()){
                //使用shiro验证
                token.setRememberMe(true);
                //验证用户角色和权限
                currentUser.login(token);
                result.put("success",true);
            }
        }catch (Exception e){
            result.put("status","error");
            e.printStackTrace();
        }

        return result;
//        return JSONUtils.toJSONString(result);
    }
    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout.json",method = RequestMethod.POST)
    @ResponseBody
    public Map logout(){
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("success", true);
        org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return result;
//        return JSONUtils.toJSONString(result);
    }

}
