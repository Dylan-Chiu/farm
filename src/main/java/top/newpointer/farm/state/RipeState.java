package top.newpointer.farm.state;

import top.newpointer.farm.GetBeanUtil;
import top.newpointer.farm.service.PlantService;


public class RipeState extends PlantState {

    private PlantService plantService = GetBeanUtil.getBean(PlantService.class);

    public static final Integer CODE = 3;

    @Override
    public Integer getCODE() {
        return CODE;
    }

    @Override
    public void updateTime() {

    }

    @Override
    public String water() {
        return "植物已经成熟，不需要浇水！";
    }

    @Override
    public String harvest(Integer farmerId) {
        plantService.harvest(super.plant, farmerId);
        return "成功收获！";
    }

    @Override
    public Integer steal(Integer farmerId) {
        return plantService.steal(super.plant, farmerId);
    }

    @Override
    public void startAccelerate(Plant plant, Double delta) {

    }

    @Override
    public void endAccelerate(Plant plant, Double delta, Integer time) {

    }


}
