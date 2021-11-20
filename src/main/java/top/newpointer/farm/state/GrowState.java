package top.newpointer.farm.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.newpointer.farm.service.PlantService;

public class GrowState extends PlantState{

    public static final Integer CODE = 0;

    @Autowired
    private PlantService plantService;

    @Override
    public void grow() {
        plantService.grow(super.plant);
        if(super.plant.getRestTime() == 0) {//剩余成熟时间为0，更新状态
            super.plant.setPlantState(Plant.RIPE_STATE);
        }
    }

    @Override
    public String water() {
        return "植物正在健康生长，无需浇水！";
    }

    @Override
    public String harvest() {
        return "植物正在健康生长，还未到收获期！";
    }
}
