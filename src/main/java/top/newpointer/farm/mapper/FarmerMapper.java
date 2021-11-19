package top.newpointer.farm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.newpointer.farm.pojo.Farmer;

@Mapper
public interface FarmerMapper extends BaseMapper<Farmer> {
}
