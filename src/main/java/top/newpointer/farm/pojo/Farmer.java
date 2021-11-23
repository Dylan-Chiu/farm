package top.newpointer.farm.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Farmer {
    private Integer id;
    private String username;
    private String password;
    private String Nickname;
    @TableField(exist = false)
    private Integer level;
    private Integer experience;
    private double money;

    @TableField(exist = false)
    private Integer currentExpLen;//当前等级的经验框长度
}
