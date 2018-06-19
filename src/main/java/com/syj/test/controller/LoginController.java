package com.syj.test.controller;

import com.syj.test.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Controller
public class LoginController {

    @Autowired
    UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(path = {"/reglogin"}, method = {RequestMethod.GET})
    public String reg(Model model,
                      @RequestParam(value = "next", required = false) String next){
        model.addAttribute("next", next);
        return "login";
    }

    @RequestMapping(value = "/reg/", method = {RequestMethod.POST})
    public String register(Model model,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam(value = "next", required = false) String next,
                           HttpServletResponse response){
        try {
            logger.info("i am here 1");
            Map<String, String> map = userService.register(username, password);
            logger.info("i am import");
            if(map.containsKey("ticket")){
                logger.info("i am here 2");
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);
                logger.info("i am here 3");
                if(StringUtils.isEmpty(next)){
                    logger.info("i am here 4");
                    return "redirect:/";
                }else{
                    logger.info("i am here 5");
                    return "redirect:" + next;
                }
            }else{
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
        } catch (Exception e) {
            logger.error("注册异常", e.getMessage());
            return "login";
        }
    }

    @RequestMapping(path = {"/login/"}, method = {RequestMethod.POST})
    public String login(Model model,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "next", required = false) String next,
                        @RequestParam(value = "rememberme", defaultValue = "false") boolean remeberme,
                        HttpServletResponse response){
        try {
            Map<String,String> map = userService.login(username,password);
            if(map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);
                if(StringUtils.isEmpty(next)){
                    return "redirect:/";
                }else{
                    return "redirect:" + next;
                }

            }else{
                model.addAttribute("msg",map.get("msg"));
                return "login";
            }
        } catch (Exception e) {
            logger.info("登录异常", e.getMessage());
            return "login";
        }
    }

    @RequestMapping(value = {"/logout"},method = {RequestMethod.GET})
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return "redirect:/";
    }
}
