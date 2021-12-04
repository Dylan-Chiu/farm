package top.newpointer.farm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.newpointer.farm.GetBeanUtil;
import top.newpointer.farm.service.RedisService;
import top.newpointer.farm.utils.Message;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RedisService redisService;

    @RequestMapping("/setDryProbability")
    public String setDryProbability(@RequestParam("dryProbability") Double dryProbability) {
        redisService.set("dryProbability", dryProbability);
        return new Message().toString();
    }

    @RequestMapping("/getDryProbability")
    public String getDryProbability() {
        Double dryProbability = (Double) redisService.get("dryProbability");
        if(dryProbability == null) {
            return GetBeanUtil.getPropertiesValue("dryProbability");
        }
        return dryProbability.toString();
    }
}
