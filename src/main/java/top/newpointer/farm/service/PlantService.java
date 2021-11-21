package top.newpointer.farm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.newpointer.farm.Signleton.PlantSet;
import top.newpointer.farm.mapper.PlantMapper;
import top.newpointer.farm.mapper.SpeciesMapper;
import top.newpointer.farm.state.GrowState;
import top.newpointer.farm.state.Plant;
import top.newpointer.farm.state.PlantState;

import java.util.Date;
import java.util.List;

@Service
public class PlantService {

    @Autowired
    private SpeciesMapper speciesMapper;

    @Autowired
    private PlantMapper plantMapper;

    @Autowired
    LandService landService;

    @Value("${maxLandNumber}")
    private Integer maxLandNumber;

    public void addPlant(Integer farmerId, Integer landId, Integer speciesId) {
        double restTime = speciesMapper.selectById(speciesId).getGrowthTime();
//        Plant plant = new Plant(null, farmerId, landId, speciesId, new Date(), restTime, 1, new GrowState());
        Plant plant = new Plant();
        plant.setFarmerId(farmerId);
        plant.setLandId(landId);
        plant.setSpeciesId(speciesId);
        plant.setSowingTime(new Date());
        plant.setRestTime(restTime);
        plant.setGrowthRate(1);
        plant.setPlantState(new GrowState());
        PlantSet.getInstance().addPlant(plant);
        plantMapper.insert(plant);
    }

    public Plant[] getPlantsByFarmerId(int farmerId) {
        List<Plant> plantList = PlantSet.getInstance().getPlantsByFarmerId(farmerId);

        Plant[] plantArray = new Plant[maxLandNumber];
        for (Plant plant : plantList) {
            Integer landId = plant.getLandId();
            plantArray[landId] = plant;
        }
        return plantArray;
    }

    public void removePlantByFarmerIdAndLandId(int farmerId, int landId) {
        Plant selected = PlantSet.getInstance().getPlantByFarmerIdAndLandId(farmerId, landId);
        selected.dig();
    }

    public void grow(Plant plant) {
        double after = plant.getRestTime() - plant.getGrowthRate();
        plant.setRestTime(after > 0 ? after : 0);
    }
}
