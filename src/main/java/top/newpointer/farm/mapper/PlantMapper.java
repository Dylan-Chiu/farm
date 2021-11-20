package top.newpointer.farm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.newpointer.farm.state.Plant;

@Mapper
public interface PlantMapper extends BaseMapper<Plant> {
}
