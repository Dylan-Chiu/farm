package top.newpointer.farm.state;

public class WaterState extends PlantState{

    public static final Integer CODE = -2;

    @Override
    public Integer getCODE() {
        return CODE;
    }

    @Override
    public void grow() {
    }

    @Override
    public String water() {
        super.plant.setPlantState(new GrowState());
        return "植物浇水成功！";
    }

    @Override
    public String harvest() {
        return "植物缺水中，无法收获！";
    }

    @Override
    public void beNeedWaterAtProbability(Double p) {

    }

}
