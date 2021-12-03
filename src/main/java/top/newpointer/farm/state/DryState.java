package top.newpointer.farm.state;

import top.newpointer.farm.GetBeanUtil;
import top.newpointer.farm.service.InteractLogService;
import top.newpointer.farm.service.PlantService;

public class DryState extends PlantState{

    private PlantService plantService = GetBeanUtil.getBean(PlantService.class);

    public static final Integer CODE = 2;

    @Override
    public Integer getCODE() {
        return CODE;
    }

    @Override
    public void updateTime() {
        plantService.dying(plant);
        if(plant.getTimeToDeath() == 0) {
            super.plant.setPlantState(new DeadState());
        }
    }

    @Override
    public String water() {
        super.plant.setPlantState(new GrowState());
        return "植物浇水成功！";
    }

    @Override
    public String harvest(Integer farmerId) {
        return "植物缺水中，无法收获！";
    }

    @Override
    public Integer steal(Integer farmerId) {
        return null;
    }

    @Override
    public void startAccelerate(Plant plant, Double delta) {

    }

    @Override
    public void endAccelerate(Plant plant, Double delta, Integer time) {

    }


}
