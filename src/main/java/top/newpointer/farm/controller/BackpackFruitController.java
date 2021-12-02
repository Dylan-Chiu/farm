package top.newpointer.farm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.newpointer.farm.pojo.BackpackFruit;
import top.newpointer.farm.proxy_factory.Buyer;
import top.newpointer.farm.proxy_factory.SystemBuyer;
import top.newpointer.farm.service.BackpackFruitService;
import top.newpointer.farm.utils.Message;
import top.newpointer.farm.utils.StatusCode;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/BackpackFruit")
public class BackpackFruitController {

    @Autowired
    private BackpackFruitService backpackFruitService;

    @Autowired
    private SystemBuyer systemBuyer;

    @RequestMapping("/getFruits")
    public String getFruits(HttpServletRequest request) {
        Message message = new Message();
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        List<BackpackFruit> fruits = backpackFruitService.getFruitsByFarmerId(farmerId);
        message.put("fruitList",fruits);
        return message.toString();
    }

    @RequestMapping("/sellFruit")
    public String sellFruit(HttpServletRequest request,
                            @RequestParam("speciesId") Integer speciesId,
                            @RequestParam("number") Integer number) {
        Message message = new Message();
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        //判断背包数量是否足够
        Integer beforeFruitNumber = backpackFruitService.getOneFruitNumber(farmerId, speciesId);
        if (beforeFruitNumber < number) {
            message.setState(StatusCode.NUMBER_NOT_ENOUGH);
            return message.toString();
        }
        //售卖
        Double money = systemBuyer.sell(farmerId, speciesId, number);
        message.put("money",money);
        return message.toString();
    }
}
