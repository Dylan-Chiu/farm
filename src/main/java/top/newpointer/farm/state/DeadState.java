package top.newpointer.farm.state;

import org.springframework.stereotype.Service;
import top.newpointer.farm.pojo.Farmer;

public class DeadState extends PlantState{

    public static final Integer CODE = 1;

    @Override
    public Integer getCODE() {
        return CODE;
    }

    @Override
    public void updateTime() {

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
