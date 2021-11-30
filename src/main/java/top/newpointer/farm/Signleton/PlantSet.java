package top.newpointer.farm.Signleton;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.newpointer.farm.mapper.PlantMapper;
import top.newpointer.farm.state.*;

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

        //状态模式对象更新
        for (Plant plant : plants) {
            if(plant.getState().equals(GrowState.CODE)) {
                plant.setPlantState(new GrowState());
            } else if(plant.getState().equals(DeadState.CODE)) {
                plant.setPlantState(new DeadState());
            } else if(plant.getState().equals(DryState.CODE)) {
                plant.setPlantState(new DryState());
            } else if(plant.getState().equals(RipeState.CODE)) {
                plant.setPlantState(new RipeState());
            }
        }
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

    /**
     * 根据FarmerId获取植物列表
     * @param id FarmerId
     * @return 植物列表
     */
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
            plant.grow();
        }
    }

    public void beNeedWaterAtProbability(Double p) {
        for (Plant plant : plants) {
            plant.beNeedWaterAtProbability(p);
        }
    }

    public Plant getPlantById(Integer plantId) {
        for (Plant plant : plants) {
            if (Objects.equals(plant.getId(), plantId)) {
                return plant;
            }
        }
        return null;
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

    public void updateDying() {
        for (Plant plant : plants) {
            plant.dying();
        }
    }
}
