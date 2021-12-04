package top.newpointer.farm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.newpointer.farm.pojo.Farmer;
import top.newpointer.farm.service.FarmerService;
import top.newpointer.farm.service.FriendService;
import top.newpointer.farm.utils.Message;
import top.newpointer.farm.utils.StatusCode;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private FarmerService farmerService;

    @RequestMapping("/searchFarmerByNickname")
    public String searchFarmerByNickname(@RequestParam("nickname") String nickname) {
        Message message = new Message();
        Farmer farmer = farmerService.searchFarmerByNickname(nickname);
        if (farmer == null) {
            message.setState(StatusCode.NO_RESULT);
        } else {
            message.put("selectedFarmer", farmer);
        }
        return message.toString();
    }

    @RequestMapping("/applyForFriend")
    public String applyForFriend(HttpServletRequest request,
                                 @RequestParam("friendId") Integer friendId) {
        Message message = new Message();
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        Integer code = friendService.applyForFriend(farmerId, friendId);
        message.setState(code);
        return message.toString();
    }

    @RequestMapping("/getFriendApplicationList")
    public String getFriendApplicationList(HttpServletRequest request) {
        Message message = new Message();
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        List<Farmer> friendApplicationList = friendService.getFriendApplicationList(farmerId);
        message.put("friendApplicationList", friendApplicationList);
        return message.toString();
    }

    @RequestMapping("/agreeFriend")
    public String agreeFriend(HttpServletRequest request,
                              @RequestParam("friendId") Integer friendId) {
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        Integer code = friendService.agreeFriend(farmerId, friendId);
        Message message = new Message();
        message.setState(code);
        return message.toString();
    }

    @RequestMapping("/getFriendList")
    public String getFriendList(HttpServletRequest request) {
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        List<Farmer> friends = friendService.getFriends(farmerId);
        Message message = new Message();
        message.put("friendList", friends);
        return message.toString();
    }

    @RequestMapping("/rejectFriend")
    public String rejectFriend(HttpServletRequest request,
                               @RequestParam("friendId") Integer friendId) {
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        Integer code = friendService.rejectFriend(farmerId, friendId);
        Message message = new Message();
        message.setState(code);
        return message.toString();
    }

    @RequestMapping("/deleteFriend")
    public String deleteFriend(HttpServletRequest request,
                               @RequestParam("friendId") Integer friendId) {
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        Integer code = friendService.deleteFriend(farmerId, friendId);
        Message message = new Message();
        message.setState(code);
        return message.toString();
    }
}
