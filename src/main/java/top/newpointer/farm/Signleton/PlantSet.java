package top.newpointer.farm.Signleton;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.newpointer.farm.mapper.PlantMapper;
import top.newpointer.farm.pojo.Plant;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class PlantSet {
    @Autowired
    private PlantMapper plantMapper;

    private static PlantSet instance = new PlantSet();

    @PostConstruct
    public void init() {
        instance = this;
        instance.plantMapper = this.plantMapper;

        //读取数据库数据，初始化PlantSet
        QueryWrapper<Plant> wrapper = new QueryWrapper<>();
        instance.plants = plantMapper.selectList(wrapper);
    }

    private List<Plant> plants = null;

    private PlantSet() {
    }

    public static PlantSet getInstance() {
        return instance;
    }

    public List<Plant> getAllPlants() {
        return plants;
    }

    public List<Plant> getPlantsByFarmerId(int id) {
        List<Plant> selected = new ArrayList<>();
        for (Plant plant : plants) {
            if (plant.getFarmerId().equals(id)) {
                selected.add(plant);
            }
        }
        return selected;
    }

    public void addPlant(Plant plant) {
        this.plants.add(plant);
    }

    public void updatePlantsIntoDatabase() {
        for (Plant plant : plants) {
            plantMapper.updateById(plant);
        }
    }

    public void updateRestTime() {
        for (Plant plant : plants) {
            //状态为生长状态的植物更新剩余时间
            if (Objects.equals(plant.getState(), Plant.STATE_GROW)) {
                double after = plant.getRestTime() - plant.getGrowthRate();
                plant.setRestTime(after > 0 ? after : 0);
            }

            //生长结束的植物更新状态
            if (Objects.equals(plant.getState(), Plant.STATE_GROW) && plant.getRestTime() == 0) {
                plant.setState(Plant.STATE_RIPE);
            }
        }
    }


    /**
     * 同时从PlantSet和数据库中删除植物
     */
    public void removePlant(Plant plant) {
        Integer id = plant.getId();
        UpdateWrapper<Plant> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id);
        plantMapper.delete(wrapper);
        plants.remove(plant);
    }

    public Plant getPlantByFarmerIdAndLandId(int farmerId, int landId) {
        Plant selected = null;
        for (Plant plant : plants) {
            if (plant.getFarmerId().equals(farmerId) &&
                    plant.getLandId().equals(landId)) {
                selected = plant;
                break;
            }
        }
        return selected;
    }
}
