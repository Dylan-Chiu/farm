package top.newpointer.farm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.newpointer.farm.service.PlantService;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private PlantService plantService;

    @RequestMapping("/t1")
    public String hello() {
        return "hello 7";
    }

    @RequestMapping("/t2")
    public String test1() {
        System.out.println(redisTemplate);
        return null;
    }

    @RequestMapping("/t3")
    public String test3() {
        return null;
    }
}
