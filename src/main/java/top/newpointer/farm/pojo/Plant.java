package top.newpointer.farm.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
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
    public static final Integer STATE_GROW = 0;
    public static final Integer STATE_DEATH = -1;
    public static final Integer STATE_WATER = -2;
    public static final Integer STATE_RIPE = 1;
}
