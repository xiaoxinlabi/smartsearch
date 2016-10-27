package info.puton.product.smartsearch.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by taoyang on 2016/10/27.
 */
@Controller
@RequestMapping(value="/user")
public class UserController {

    @Value("#{settings['successUrl']}")
    private String successUrl;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(
            @RequestParam(value = "username") String username
            , @RequestParam(value = "password") String password) {
        Subject subject = SecurityUtils.getSubject();
            if (subject.isAuthenticated()) {
                System.out.println("already");
                return "redirect:"+ successUrl;
            } else {
                try{
                    subject.login(new UsernamePasswordToken(username, password));
                    //TODO
//                final User userInfo = userService.selectByUsername(user.getUsername());
//                request.getSession().setAttribute("userInfo", userInfo);
                return "redirect:" + successUrl;
                } catch (AuthenticationException e) {
                    //TODO
                    return "redirect:/simple-login.html";
                }
            }

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/index.html";
    }



}
