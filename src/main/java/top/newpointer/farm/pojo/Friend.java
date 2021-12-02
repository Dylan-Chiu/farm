package top.newpointer.farm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Friend {
    private Integer farmerId;
    private Integer friendId;
}
