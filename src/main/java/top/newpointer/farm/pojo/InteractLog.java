package top.newpointer.farm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InteractLog {
    private Integer id;
    private Integer farmerId;
    private Integer friendId;
    private Integer speciesId;
    private Integer number;
    private Integer type;
    private Date time;

    public static final int STEAL = 1;
    public static final int WATER = 2;
}
