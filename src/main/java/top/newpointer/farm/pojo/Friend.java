package top.newpointer.farm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friend {
    private Integer farmerId;
    private Integer friendId;
}

