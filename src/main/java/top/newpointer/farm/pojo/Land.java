package top.newpointer.farm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Land {
    private Integer farmerId;
    private Integer landId;
    private Integer type;

    public static final Integer TYPE_LOCKED = 0;
    public static final Integer TYPE_YELLOW = 1;
    public static final Integer TYPE_RED = 2;
    public static final Integer TYPE_BLACK = 3;
    public static final Integer TYPE_DRY = -1;


}
