package top.newpointer.farm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.newpointer.farm.mapper.FarmerMapper;
import top.newpointer.farm.mapper.LandMapper;
import top.newpointer.farm.pojo.Farmer;
import top.newpointer.farm.pojo.Land;
import top.newpointer.farm.utils.Message;
import top.newpointer.farm.utils.Status;

import java.util.UUID;

@Service
public class IdentityService {

    @Autowired
    private FarmerMapper farmerMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private LandService landService;

    public String login(Farmer farmer) {
        Message massage = new Message();
        QueryWrapper<Farmer> wrapper = new QueryWrapper<>();
        wrapper.eq("username", farmer.getUsername());
        Farmer selected = farmerMapper.selectOne(wrapper);
        if (selected == null) { //用户不存在
            massage.setState(Status.USER_NOT_EXIST);
        } else {
            if (!selected.getPassword().equals(farmer.getPassword())) { //密码不等
                massage.setState(Status.PASSWORD_ERROR);
            } else { // 成功登陆
                String token = UUID.randomUUID().toString();
                redisService.set(token, selected.getId());
                massage.put("token", token);
            }
        }
        return massage.toString();
    }

    public String register(Farmer farmer) {
        farmer.setMoney(2000);
        farmer.setLevel(1);
        farmerMapper.insert(farmer);
        landService.initLandsByFarmerId(farmer.getId());
        return new Message().toString();
    }

    public String logout(String token) {
        redisService.delete(token);
        return new Message().toString();
    }

}
