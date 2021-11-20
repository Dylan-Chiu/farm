package top.newpointer.farm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.newpointer.farm.pojo.Plant;
import top.newpointer.farm.pojo.Species;
import top.newpointer.farm.service.PlantService;
import top.newpointer.farm.service.SpeciesService;
import top.newpointer.farm.utils.Message;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/plant")
public class PlantController {

    @Autowired
    private PlantService plantService;

    @Autowired
    private SpeciesService speciesService;

    @RequestMapping("/addPlant")
    public String addPlant(HttpServletRequest request,
                           @RequestParam("landId") Integer landId,
                           @RequestParam("speciesId") Integer speciesId) {
        Message message = new Message();
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        plantService.addPlant(farmerId, landId, speciesId);
        return message.toString();
    }

    @RequestMapping("/getPlantsByFarmerId")
    public String getPlantsByFarmerId(HttpServletRequest request) {
        Message message = new Message();
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
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
}
