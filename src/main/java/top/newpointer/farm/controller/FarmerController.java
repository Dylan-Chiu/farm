package top.newpointer.farm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.newpointer.farm.pojo.Farmer;
import top.newpointer.farm.service.FarmerService;
import top.newpointer.farm.utils.Message;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/farmer")
public class FarmerController {

    @Autowired
    private FarmerService farmerService;

    @RequestMapping("/getFarmer")
    private String getFarmerById(HttpServletRequest request) {
        Message message = new Message();
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        Farmer farmer = farmerService.getFarmerById(farmerId);
        //写入等级以及经验框大小
        farmerService.updateLevelAndExpLen(farmer);
        message.put("farmer",farmer);
        return message.toString();
    }
}
