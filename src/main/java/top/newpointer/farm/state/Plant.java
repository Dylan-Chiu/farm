package top.newpointer.farm.state;

import com.baomidou.mybatisplus.annotation.TableField;
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
    private Integer state;//数据库字段，保存状态
    private double restTime;
    private double growthRate;

    @TableField(exist = false)
    private PlantState plantState;//状态模式字段，不存入数据库，要确保和state同步

    /**
     * 设置植物状态，更新两个成员变量（PlantState和State）
     *
     * @param plantState
     */
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
