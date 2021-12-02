package top.newpointer.farm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.newpointer.farm.pojo.BackpackFruit;
import top.newpointer.farm.proxy_factory.Buyer;
import top.newpointer.farm.proxy_factory.Jack;
import top.newpointer.farm.proxy_factory.SystemBuyer;
import top.newpointer.farm.proxy_factory.Tom;
import top.newpointer.farm.service.BackpackFruitService;
import top.newpointer.farm.service.FarmerService;
import top.newpointer.farm.utils.Message;
import top.newpointer.farm.utils.StatusCode;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/BackpackFruit")
public class BackpackFruitController {

    @Autowired
    private FarmerService farmerService;

    @Autowired
    private BackpackFruitService backpackFruitService;

    @Autowired
    private SystemBuyer systemBuyer;

    @Autowired
    private Jack jack;

    @Autowired
    private Tom tom;

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
                            @RequestParam("number") Integer number,
                            @RequestParam("identityCode") int code) {
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        Message message = backpackFruitService.sellFruit(farmerId, speciesId, number, code);
        return message.toString();
    }
}
