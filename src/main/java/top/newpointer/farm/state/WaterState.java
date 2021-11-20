package top.newpointer.farm.state;

public class WaterState extends PlantState{

    public static final Integer CODE = -2;

    @Override
    public void grow() {
    }

    @Override
    public String water() {
        super.plant.setPlantState(Plant.GROW_STATE);
        return "植物浇水成功！";
    }

    @Override
    public String harvest() {
        return "植物缺水中，无法收获！";
    }

}
