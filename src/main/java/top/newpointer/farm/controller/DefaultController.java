package top.newpointer.farm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
    @RequestMapping("/")
    public String hello(){
        return "redirect:static/dist/index.html";
    }
}