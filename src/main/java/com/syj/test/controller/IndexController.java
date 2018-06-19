package com.syj.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//这是一个测试页面
//@Controller
public class IndexController {

    @RequestMapping("/")
    @ResponseBody
    public String index(){
        return "OK!!!";
    }
}
