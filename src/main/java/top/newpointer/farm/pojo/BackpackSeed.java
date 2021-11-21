package top.newpointer.farm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackpackSeed {
    private Integer farmerId;
    private Integer speciesId;
    private Integer number;
}
