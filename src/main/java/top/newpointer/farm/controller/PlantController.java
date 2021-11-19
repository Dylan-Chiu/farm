package top.newpointer.farm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.newpointer.farm.pojo.Plant;
import top.newpointer.farm.service.PlantService;
import top.newpointer.farm.utils.Message;
import top.newpointer.farm.utils.Status;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/plant")
public class PlantController {

    @Autowired
    private PlantService plantService;

    @RequestMapping("/addPlant")
    public String addPlant(HttpServletRequest request,
                         @RequestParam("landId") Integer landId,
                         @RequestParam("speciesId") Integer speciesId) {
        Message message = new Message();
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        if(farmerId == null) {
            message.setState(Status.NO_LOGIN);
            return message.toString();
        }
        plantService.addPlant(farmerId, landId, speciesId);
        return message.toString();
    }

    @RequestMapping("/getPlantsByFarmerId")
    public String getPlantsByFarmerId(HttpServletRequest request) {
        Message message = new Message();
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        if(farmerId == null) {
            message.setState(Status.NO_LOGIN);
            return message.toString();
        }
        Plant[] plants = plantService.getPlantsByFarmerId(farmerId);
        message.put("plantList",plants);
        return message.toString();
    }
}
