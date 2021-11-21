package top.newpointer.farm.state;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Plant {
    private Integer id;
    private Integer farmerId;
    private Integer landId;
    private Integer speciesId;
    private Date sowingTime;
    private Integer state;
    private double restTime;
    private double growthRate;

    //植物的各种状态
    public static final GrowState GROW_STATE = new GrowState();
    public static final DeadState DEAD_STATE = new DeadState();
    public static final WaterState WATER_STATE = new WaterState();
    public static final RipeState RIPE_STATE = new RipeState();

    @TableField(exist = false)
    private PlantState plantState;

    public void setPlantState(PlantState plantState) {
        this.plantState = plantState;
        this.plantState.setPlant(this);
        this.setState(plantState.getCODE());
    }

    public void grow() {
        this.plantState.grow();
    }

    public void water() {
        this.plantState.water();
    }

    public void harvest() {
        this.plantState.harvest();
    }

    public void dig() {
        this.plantState.dig();
    }
}
