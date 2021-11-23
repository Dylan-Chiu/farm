package top.newpointer.farm.state;

import top.newpointer.farm.Signleton.PlantSet;
import top.newpointer.farm.pojo.Farmer;

public abstract class PlantState {
    protected Plant plant;

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    /**
     * 返回当前状态类的状态码，以便修改Plant中的state字段
     * @return 状态码
     */
    public abstract Integer getCODE();

    public abstract void grow();
    public abstract String water();
    public abstract String harvest(Integer farmerId);
    //概率成为缺水状态
    public abstract void beNeedWaterAtProbability(Double p);
    /**
     * 所有状态下执行相同操作
     */
    public void dig() {
        PlantSet.getInstance().removePlant(plant);
    }
}
