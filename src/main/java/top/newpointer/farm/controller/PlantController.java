package top.newpointer.farm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.newpointer.farm.Signleton.PlantSet;
import top.newpointer.farm.service.InteractLogService;
import top.newpointer.farm.state.Plant;
import top.newpointer.farm.pojo.Species;
import top.newpointer.farm.service.PlantService;
import top.newpointer.farm.service.SpeciesService;
import top.newpointer.farm.utils.Message;
import top.newpointer.farm.utils.StatusCode;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/plant")
public class PlantController {

    @Autowired
    private PlantService plantService;

    @Autowired
    private SpeciesService speciesService;

    @Autowired
    private InteractLogService interactLogService;

    @RequestMapping("/sowSeed")
    public String sowSeed(HttpServletRequest request,
                          @RequestParam("landId") Integer landId,
                          @RequestParam("speciesId") Integer speciesId) {
        Message message = new Message();
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        Integer statusCode = plantService.sowSeed(farmerId, landId, speciesId);
        message.setState(statusCode);
        return message.toString();
    }

    @RequestMapping("/getPlantsByFarmerId")
    public String getPlantsByFarmerId(HttpServletRequest request,
                                      @RequestParam(value = "friendId",required = false) Integer friendId) {
        Message message = new Message();
        Integer farmerId;
        if (friendId == null) {
            farmerId = (Integer) request.getSession().getAttribute("farmerId");
        }else {
            farmerId = friendId;
        }
        Plant[] plants = plantService.getPlantsByFarmerId(farmerId);
        Species[] speciesArray = speciesService.getSpeciesArrayByPlants(plants);
        message.put("plantList", plants);
        message.put("speciesList", speciesArray);
        return message.toString();
    }

    @RequestMapping("/removePlantByFarmerIdAndLandId")
    public String removePlantByFarmerIdAndLandId(HttpServletRequest request,
                                                 @RequestParam("landId") Integer landId) {
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        plantService.removePlantByFarmerIdAndLandId(farmerId, landId);
        return new Message().toString();
    }

    /**
     * 浇水
     *
     * @return
     */
    @RequestMapping("/water")
    public String water(HttpServletRequest request,
                        @RequestParam("landId") Integer landId) {
        Message message = new Message();
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        Plant plant = PlantSet.getInstance().getPlantByFarmerIdAndLandId(farmerId, landId);
        plant.water();
        return message.toString();
    }

    /**
     * 帮好友浇水
     * @return
     */
    @RequestMapping("/waterFriend")
    public String waterFriend(HttpServletRequest request,
                              @RequestParam("friendId") Integer friendId,
                              @RequestParam("landId") Integer landId) {
        Message message = new Message();
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        Plant plant = PlantSet.getInstance().getPlantByFarmerIdAndLandId(friendId, landId);
        plant.water();
        interactLogService.recordWater(farmerId, friendId);
        return message.toString();
    }

    @RequestMapping("/harvest")
    public String harvest(HttpServletRequest request,
                          @RequestParam("landId") Integer landId) {
        Message message = new Message();
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        Plant plant = PlantSet.getInstance().getPlantByFarmerIdAndLandId(farmerId, landId);
        plant.harvest();
        //果实数
        Integer fruitNumber = plant.getFruitNumber();
        Integer speciesId = plant.getSpeciesId();
        Species species = speciesService.getSpeciesById(speciesId);
        message.put("fruitNumber", fruitNumber);
        message.put("species", species);
        return message.toString();
    }

    @RequestMapping("/steal")
    public String steal(HttpServletRequest request,
                        @RequestParam("friendId") Integer friendId,
                        @RequestParam("landId") Integer landId) {
        Message message = new Message();
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");//偷盗者
        Plant plant = PlantSet.getInstance().getPlantByFarmerIdAndLandId(friendId, landId);
        Integer fruitNumber = plant.steal(farmerId);
        Integer speciesId = plant.getSpeciesId();
        Species species = speciesService.getSpeciesById(speciesId);
        message.put("fruitNumber", fruitNumber);
        message.put("species", species);
        return message.toString();
    }

    @RequestMapping("/buyAcceleration")
    public String buyAcceleration(HttpServletRequest request,
                                  @RequestParam("landId") Integer landId) {
        Message message = new Message();
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        Plant plant = PlantSet.getInstance().getPlantByFarmerIdAndLandId(farmerId, landId);
        Boolean isSucceed = plantService.buyAcceleration(farmerId, plant);
        if (isSucceed == false) {
            message.setState(StatusCode.MONEY_NOT_ENOUGH);
        } else {
            plantService.accelerate(plant);
        }
        return message.toString();
    }
}
