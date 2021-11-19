package top.newpointer.farm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.newpointer.farm.mapper.LandMapper;
import top.newpointer.farm.pojo.Land;

import java.util.List;

@Service
public class LandService {

    @Autowired
    private LandMapper landMapper;

    @Value("${maxLandNumber}")
    private Integer maxLandNumber;

    @Value("${unlockedLandNumber}")
    private Integer unlockedLandNumber;

    public List<Land> getLandListByFarmerId(Integer farmerId) {
        QueryWrapper<Land> wrapper = new QueryWrapper<>();
        wrapper.eq("farmer_id",farmerId);
        List<Land> landList = landMapper.selectList(wrapper);
        return landList;
    }

    /**
     * 给新注册的农民分配初始土地，前unlockedLandNumber块土地解锁
     * @param farmerId 农民Id
     */
    public void initLandsByFarmerId(Integer farmerId) {
        for (Integer i = 0; i < maxLandNumber; i++) {
            Land land = new Land(farmerId,i,Land.TYPE_LOCKED);
            if(i < unlockedLandNumber) {
                land.setType(Land.TYPE_YELLOW);
            }
            landMapper.insert(land);
        }
    }
}
