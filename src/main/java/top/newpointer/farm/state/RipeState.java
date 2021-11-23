package top.newpointer.farm.state;

import top.newpointer.farm.GetBeanUtil;
import top.newpointer.farm.pojo.Farmer;
import top.newpointer.farm.service.PlantService;

import java.util.Map;

public class RipeState extends PlantState {

    private PlantService plantService = GetBeanUtil.getBean(PlantService.class);

    public static final Integer CODE = 3;

    @Override
    public Integer getCODE() {
        return CODE;
    }

    @Override
    public void grow() {
    }

    @Override
    public String water() {
        return "植物已经成熟，不需要浇水！";
    }

    @Override
    public String harvest(Integer farmerId) {
        Map<String, Double> data =  plantService.harvest(super.plant, farmerId);
        return "成功收获！\n" +
                "获得金钱：" + data.get("money") + "!\n" +
                "获取经验值：" + data.get("experience") + "!";
    }

    @Override
    public void beNeedWaterAtProbability(Double p) {

    }
}
