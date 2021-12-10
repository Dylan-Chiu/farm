package top.newpointer.farm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.newpointer.farm.pojo.Farmer;
import top.newpointer.farm.service.IdentityService;
import top.newpointer.farm.service.LandService;
import top.newpointer.farm.utils.ImageUtil;
import top.newpointer.farm.utils.Message;
import top.newpointer.farm.utils.StatusCode;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;

@RestController
@RequestMapping("/identity")
public class IdentityController {

    @Autowired
    private IdentityService identityService;

    @RequestMapping("/login")
    public String login(@RequestBody Farmer farmer) {
        String message = identityService.login(farmer);
        return message;
    }

    @RequestMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String nickname,
                           @RequestParam(value = "img", required = false) MultipartFile img) {
        //验证用户名重复
        if(identityService.isUsernameRepeated(username) == true) {
            Message message = new Message();
            message.setState(StatusCode.USERNAME_REPEATED);
            return message.toString();
        }
        if(identityService.isNicknameRepeated(nickname) == true) {
            Message message = new Message();
            message.setState(StatusCode.NICKNAME_REPEATED);
            return message.toString();
        }
        //验证昵称重复
        //存储头像图片
        String imgName = ImageUtil.savePortraitImg(img);
        Farmer farmer = new Farmer();
        farmer.setUsername(username);
        farmer.setPassword(password);
        farmer.setNickname(nickname);
        farmer.setHeadPortrait(imgName);
        String message = identityService.register(farmer);
        return message;
    }



    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        String message = identityService.logout(token);
        return message;
    }
}
