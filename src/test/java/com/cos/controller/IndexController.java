package com.cos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping({"","/"})
    public String index(){
        //머스테치 기본 폴더  src/main/resources/
        //viewresolver : templates(prefix).mustache(suffix)
        return "index";
    }
}
