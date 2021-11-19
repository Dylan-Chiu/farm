package top.newpointer.farm.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/")
public class TestController {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @RequestMapping("/hello")
    public String hello() {
        return "hello alibaba sh sh again";
    }

    @RequestMapping("/test1")
    public String test1() {
        System.out.println(redisTemplate);
        return null;
    }
}
