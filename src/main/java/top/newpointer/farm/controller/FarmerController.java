package top.newpointer.farm.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.newpointer.farm.pojo.Farmer;
import top.newpointer.farm.service.FarmerService;
import top.newpointer.farm.utils.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

@RestController
@RequestMapping("/farmer")
public class FarmerController {

    @Autowired
    private FarmerService farmerService;

    @Value("${linux_head_portrait_path}")
    private String linuxHeadPortraitPath;

    @RequestMapping("/getFarmer")
    private String getFarmerById(HttpServletRequest request,
                                 @RequestParam(value = "friendId", required = false) Integer friendId) {
        Message message = new Message();
        Integer farmerId;
        if (friendId == null) {
            farmerId = (Integer) request.getSession().getAttribute("farmerId");
        } else {
            farmerId = friendId;
        }
        Farmer farmer = farmerService.getFarmerById(farmerId);
        //写入等级以及经验框大小
        farmerService.updateLevelAndExpLen(farmer);
        message.put("farmer", farmer);
        return message.toString();
    }

    @SneakyThrows
    @RequestMapping(value = "/getHeadPortrait", produces = MediaType.IMAGE_JPEG_VALUE)
    private byte[] getHeadPortrait(@RequestParam("farmerId") Integer farmerId) {
        String imageName = farmerService.getFarmerById(farmerId).getHeadPortrait();
        File file = new File(linuxHeadPortraitPath + "/" + imageName);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }
}
