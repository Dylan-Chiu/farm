package top.newpointer.farm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.newpointer.farm.pojo.BackpackFruit;
import top.newpointer.farm.service.BackpackFruitService;
import top.newpointer.farm.utils.Message;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/BackpackFruit")
public class BackpackFruitController {

    @Autowired
    private BackpackFruitService backpackFruitService;

    @RequestMapping("/getFruits")
    public String getFruits(HttpServletRequest request) {
        Message message = new Message();
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        List<BackpackFruit> fruits = backpackFruitService.getFruitsByFarmerId(farmerId);
        message.put("fruitList",fruits);
        return message.toString();
    }
}
