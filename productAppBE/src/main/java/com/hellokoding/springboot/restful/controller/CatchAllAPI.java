package com.hellokoding.springboot.restful.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor

public class CatchAllAPI {

    @RequestMapping("/article/218/details")
    public String app() {
        System.out.println("!!!!!!!!!!!!!!!!Achtung");
        return "forward:/index.html";
    }

}
