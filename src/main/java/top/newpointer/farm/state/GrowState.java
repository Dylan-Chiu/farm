package top.newpointer.farm.state;

import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
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
            plantService.setTimeToDeath(plant);
        }
    }

    @Override
    public void dying() {

    }

    @Override
    public void startAccelerate(Plant plant, Double delta) {
        plant.setGrowthRate(plant.getGrowthRate() + delta);
    }

    @SneakyThrows
    @Override
    public void endAccelerate(Plant plant, Double delta,Integer time) {
        Thread.sleep(time * 1000);
        plant.setGrowthRate(plant.getGrowthRate() - delta);
    }
}
