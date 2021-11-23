package top.newpointer.farm.state;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.newpointer.farm.pojo.Farmer;
import top.newpointer.farm.pojo.Species;

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

    @TableField(exist = false)
    private Species species;

    /**
     * 设置植物状态，更新两个成员变量（plantState和state）
     *
     * @param plantState
     */
    public void setPlantState(PlantState plantState) {
        //更新plantState
        this.plantState = plantState;
        this.plantState.setPlant(this);
        //更新state
        this.setState(plantState.getCODE());
    }

    public void grow() {
        this.plantState.grow();
    }

    public String water() {
        return this.plantState.water();
    }

    public String harvest() {
        return this.plantState.harvest(farmerId);
    }

    public void dig() {
        this.plantState.dig();
    }

    /**
     * 每秒以p的概率变为需要浇水状态
     *
     * @param p
     */
    public void beNeedWaterAtProbability(Double p) {
        this.plantState.beNeedWaterAtProbability(p);
    }
}