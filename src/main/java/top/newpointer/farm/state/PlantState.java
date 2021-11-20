package top.newpointer.farm.state;

import top.newpointer.farm.Signleton.PlantSet;

public abstract class PlantState {
    protected Plant plant;

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public abstract void grow();
    public abstract String water();
    public abstract String harvest();
    public void dig() {
        PlantSet.getInstance().removePlant(plant);
    }
}
