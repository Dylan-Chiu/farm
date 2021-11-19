package top.newpointer.farm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.newpointer.farm.pojo.Farmer;
import top.newpointer.farm.service.IdentityService;

import javax.servlet.http.HttpServletRequest;

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
    public String register(@RequestBody Farmer farmer) {
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
