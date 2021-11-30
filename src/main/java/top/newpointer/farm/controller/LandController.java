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

@RestController
@RequestMapping("/land")
public class LandController {

    @Autowired
    private LandService landService;

    @RequestMapping("/getLandListByFarmerId")
    public String getLandListByFarmerId(HttpServletRequest request) {
        Message message = new Message();
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        List<Land> landList = landService.getLandListByFarmerId(farmerId);
        message.put("landList",landList);
        return message.toString();
    }

    @RequestMapping("/upgrade")
    public String upgrade(HttpServletRequest request,
                          @RequestParam("landId") Integer landId) {
        Message message = new Message();
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        Boolean isSucceed = landService.upgrade(farmerId,landId);
        if(isSucceed == false) {
            message.setState(StatusCode.MONEY_NOT_ENOUGH);
        }
        return message.toString();
    }
}
