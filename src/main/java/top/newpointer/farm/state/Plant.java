package top.newpointer.farm.state;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Async;
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
    private double timeToDeath;
    private Integer state;//数据库字段，保存状态
    private double restTime;
    //花钱加速的速率 （不包含土地的基础速率）
    private double growthRate;
    private Integer fruitNumber;

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

    public void updateTime() {
        this.plantState.updateTime();
    }

    public String water() {
        return this.plantState.water();
    }

    public String harvest() {
        return this.plantState.harvest(farmerId);
    }

    /**
     *
     * @param farmerId 偷盗者的id
     * @return
     */
    public Integer steal(Integer farmerId) {
        return this.plantState.steal(farmerId);
    }

    public void dig() {
        this.plantState.dig();
    }

    public void startAccelerate(Plant plant, Double delta) {
        this.plantState.startAccelerate(plant, delta);
    }

    public void endAccelerate(Plant plant, Double delta, Integer time) {
        this.plantState.endAccelerate(plant,delta,time);
    }
}