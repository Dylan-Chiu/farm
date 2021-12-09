package top.newpointer.farm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.newpointer.farm.pojo.Land;
import top.newpointer.farm.service.LandService;
import top.newpointer.farm.utils.Message;
import top.newpointer.farm.utils.StatusCode;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/land")
public class LandController {

    @Autowired
    private LandService landService;

    @RequestMapping("/getLandListByFarmerId")
    public String getLandListByFarmerId(HttpServletRequest request,
                                        @RequestParam(value = "friendId",required = false) Integer friendId) {
        Message message = new Message();
        Integer farmerId;
        if(friendId == null) {
            farmerId = (Integer) request.getSession().getAttribute("farmerId");
        } else {
            farmerId = friendId;
        }
        List<Land> landList = landService.getLandListByFarmerId(farmerId);
        message.put("landList",landList);
        return message.toString();
    }

    @RequestMapping("/upgrade")
    public String upgrade(HttpServletRequest request,
                          @RequestParam("landId") Integer landId) {
        Message message = new Message();
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        //判断是否满级
        Integer type = landService.getLandByFarmerIdAndLandId(farmerId,landId).getType();
        if(Objects.equals(type, Land.TYPE_BLACK)) {
            message.setState(StatusCode.ALREADY_BEST_LAND);
            return message.toString();
        }
        Boolean isSucceed = landService.upgrade(farmerId,landId);
        if(isSucceed == false) {
            message.setState(StatusCode.MONEY_NOT_ENOUGH);
        }
        return message.toString();
    }
}
