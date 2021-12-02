package top.newpointer.farm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.newpointer.farm.pojo.BackpackSeed;
import top.newpointer.farm.service.BackpackSeedService;
import top.newpointer.farm.utils.Message;
import top.newpointer.farm.utils.StatusCode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/backpackSeed")
public class BackpackSeedController {

    @Autowired
    private BackpackSeedService backpackSeedService;

    @RequestMapping("/getSeedsNumberByFarmerId")
    public String getSeedsNumberByFarmerId(HttpServletRequest request) {
        Message message = new Message();
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        List<BackpackSeed> seedsNumber = backpackSeedService.getSeedsNumberByFarmerId(farmerId);
        List<BackpackSeed> disposeZero = new ArrayList<>();
        for (BackpackSeed backpackSeed : seedsNumber) {//种子数为0不返回前端
            if (backpackSeed.getNumber() != 0) {
                disposeZero.add(backpackSeed);
            }
        }
        message.put("seedsNumber",disposeZero);
        return message.toString();
    }

    /**
     * 返回的json字段中
     *      status代表操作正常进行
     *      isSucceed代表是否购买成功
     * @param request
     * @param speciesId
     * @param number
     * @return
     */
    @RequestMapping("/buySeeds")
    public String buySeeds(HttpServletRequest request,
                           @RequestParam("speciesId") Integer speciesId,
                           @RequestParam("number") Integer number) {
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        Message message = new Message();
        Boolean isSucceed = backpackSeedService.buySeeds(farmerId, speciesId, number);
        if(!isSucceed) {
            message.setState(StatusCode.MONEY_NOT_ENOUGH);
        }
        return message.toString();
    }
}
