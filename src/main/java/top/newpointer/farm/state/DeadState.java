package top.newpointer.farm.state;

import top.newpointer.farm.pojo.Farmer;

public class DeadState extends PlantState{

    public static final Integer CODE = 1;

    @Override
    public Integer getCODE() {
        return CODE;
    }

    @Override
    public void grow() {
    }

    @Override
    public String water() {
        return "植物已经死亡，浇水不起作用了！";
    }

    @Override
    public String harvest(Integer farmerId) {
        return "植物已经死亡，无法收获了！";
    }

    @Override
    public void beNeedWaterAtProbability(Double p) {

    }

    @Override
    public void dying() {

    }

    @Override
    public void startAccelerate(Plant plant, Double delta) {

    }

    @Override
    public void endAccelerate(Plant plant, Double delta, Integer time) {

    }


}
