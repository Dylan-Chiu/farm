package top.newpointer.farm.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import top.newpointer.farm.GetBeanUtil;
import top.newpointer.farm.service.FarmerService;

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
    private String headPortrait;

    @TableField(exist = false)
    private Integer currentExpLen;//当前等级的经验框长度

    @TableField(exist = false, select = false)
    @Autowired
    private FarmerService farmerService = GetBeanUtil.getBean(FarmerService.class);

    //更改经验时自动更改等级及经验框长度
    public void setExperience(Integer experience) {
        this.experience = experience;
        Integer level = farmerService.getLevelByExperience(experience);
        this.level = level;
        Integer currentExpLen = farmerService.getCurrentExpLenByLevel(level);
        this.currentExpLen = currentExpLen;
    }
}
