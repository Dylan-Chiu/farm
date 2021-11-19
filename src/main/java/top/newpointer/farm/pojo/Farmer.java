package top.newpointer.farm.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    private Integer level;
    private double money;
}
