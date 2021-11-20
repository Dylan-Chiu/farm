package top.newpointer.farm.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @RequestMapping("/t1")
    public String hello() {
        return "hello 7";
    }

    @RequestMapping("/t2")
    public String test1() {
        System.out.println(redisTemplate);
        return null;
    }
}
