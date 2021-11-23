package top.newpointer.farm.state;

import top.newpointer.farm.pojo.Farmer;
import top.newpointer.farm.service.PlantService;
import top.newpointer.farm.GetBeanUtil;

public class GrowState extends PlantState{

    public static final Integer CODE = 0;

//  此类未注入Spring容器，则也不能使用 @Autowired获取容器中内容
    private PlantService plantService = GetBeanUtil.getBean(PlantService.class);

    @Override
    public Integer getCODE() {
        return CODE;
    }

    @Override
    public void grow() {
        plantService.grow(super.plant);
        if(super.plant.getRestTime() == 0) {//剩余成熟时间为0，更新状态
            super.plant.setPlantState(new RipeState());
        }
    }

    @Override
    public String water() {
        return "植物正在健康生长，无需浇水！";
    }

    @Override
    public String harvest(Integer farmerId) {
        return "植物正在健康生长，还未到收获期！";
    }

    @Override
    public void beNeedWaterAtProbability(Double p) {
        if(Math.random() < p ) {
            super.plant.setPlantState(new WaterState());
        }
    }
}
