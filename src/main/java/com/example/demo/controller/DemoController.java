package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DemoController {
    @GetMapping("/home")
    public ModelAndView index(){
        return new ModelAndView("index");
    }
    @GetMapping("/admin")
    public ModelAndView admin(){
        return new ModelAndView("admin");
    }
    @GetMapping("/user")
    public ModelAndView user(){
        return new ModelAndView("user");
    }
}
