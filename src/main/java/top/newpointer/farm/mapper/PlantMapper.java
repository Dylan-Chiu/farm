package top.newpointer.farm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.newpointer.farm.pojo.Farmer;
import top.newpointer.farm.pojo.Plant;

@Mapper
public interface PlantMapper extends BaseMapper<Plant> {
}
