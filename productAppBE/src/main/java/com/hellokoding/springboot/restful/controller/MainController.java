package com.hellokoding.springboot.restful.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String list(){
//        return "products"; //"countries";
        return "start"; //"countries";
//        return "index";
    }
}