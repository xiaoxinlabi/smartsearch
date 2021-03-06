package info.puton.product.smartsearch.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pauline on 16/11/1.
 */
@Controller
@RequestMapping(value="/user")
public class UserController {

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map checkLogin(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(false);
        try {
            subject.login(token);
            result.put("status", "success");
//            return "redirect:/admin.jsp";
        } catch (Exception e) {
            result.put("status", "error");
            result.put("detail", e.getLocalizedMessage());
            token.clear();
//            return "redirect:/login.html";
        }
        return result;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/index.html";
    }

}





