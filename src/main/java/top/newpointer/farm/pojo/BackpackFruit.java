package top.newpointer.farm.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BackpackFruit {
    private Integer farmerId;
    private Integer speciesId;
    private Integer number;

    @TableField(exist = false)
    private Species species;
}
