package top.newpointer.farm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.newpointer.farm.Signleton.PlantSet;
import top.newpointer.farm.mapper.PlantMapper;
import top.newpointer.farm.mapper.SpeciesMapper;
import top.newpointer.farm.pojo.Farmer;
import top.newpointer.farm.pojo.Species;
import top.newpointer.farm.state.GrowState;
import top.newpointer.farm.state.Plant;
import top.newpointer.farm.state.PlantState;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlantService {

    @Autowired
    private SpeciesMapper speciesMapper;

    @Autowired
    private PlantMapper plantMapper;

    @Autowired
    private FarmerService farmerService;

    @Autowired
    private LandService landService;

    @Autowired
    private BackpackSeedService backpackSeedService;

    @Autowired
    private SpeciesService speciesService;

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

    /**
     * @param farmerId
     * @param landId
     * @param speciesId
     * @return 播种是否成功
     */
    public Boolean sowSeed(Integer farmerId, Integer landId, Integer speciesId) {
        Integer seedNumber = backpackSeedService.getOneSeedNumber(farmerId, speciesId);
        if (seedNumber < 1) {
            return false;
        }
        //土地上添加植物
        addPlant(farmerId, landId, speciesId);
        //背包中种子数减一
        backpackSeedService.alterSeeds(farmerId, speciesId, -1);
        return true;
    }

    public Plant[] getPlantsByFarmerId(int farmerId) {
        List<Plant> plantList = PlantSet.getInstance().getPlantsByFarmerId(farmerId);

        Plant[] plantArray = new Plant[maxLandNumber];
        for (Plant plant : plantList) {
            Integer landId = plant.getLandId();
            plantArray[landId] = plant;
        }

        //装配物种信息对象
        for (Plant plant : plantArray) {
            if (plant != null) {
                Integer speciesId = plant.getSpeciesId();
                Species species = speciesService.getSpeciesById(speciesId);
                plant.setSpecies(species);
            }
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

    public String water(Plant plant) {
        return plant.water();
    }

    /**
     * 收获
     *
     * @param plant
     * @return 得到的收益
     */
    public Map<String, Double> harvest(Plant plant, Integer farmerId) {
        //获取农民对象
        Farmer farmer = farmerService.getFarmerById(farmerId);
        HashMap<String, Double> data = new HashMap<>();
        //删除该植物
        plant.dig();
        //添加收益
        Double money = farmer.getMoney();
        money += speciesService.getSpeciesById(plant.getSpeciesId()).getProfit();
        farmer.setMoney(money);
        data.put("money",money);
        return data;
    }
}
