package top.newpointer.farm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.newpointer.farm.mapper.LandMapper;
import top.newpointer.farm.pojo.Land;

import java.util.List;
import java.util.Objects;

@Service
public class LandService {

    @Autowired
    private LandMapper landMapper;

    @Autowired
    private FarmerService farmerService;

    @Value("${maxLandNumber}")
    private Integer maxLandNumber;

    @Value("${unlockedLandNumber}")
    private Integer unlockedLandNumber;

    @Value("${redUpgradeCost}")
    private Integer redUpgradeCost;
    @Value("${blackUpgradeCost}")
    private Integer blackUpgradeCost;

    public List<Land> getLandListByFarmerId(Integer farmerId) {
        QueryWrapper<Land> wrapper = new QueryWrapper<>();
        wrapper.eq("farmer_id", farmerId)
                .orderByAsc("land_id");
        List<Land> landList = landMapper.selectList(wrapper);
        return landList;
    }

    /**
     * 给新注册的农民分配初始土地，前unlockedLandNumber块土地解锁
     *
     * @param farmerId 农民Id
     */
    public void initLandsByFarmerId(Integer farmerId) {
        for (Integer i = 0; i < maxLandNumber; i++) {
            Land land = new Land(farmerId, i, Land.TYPE_LOCKED);
            if (i < unlockedLandNumber) {
                land.setType(Land.TYPE_YELLOW);
            }
            landMapper.insert(land);
        }
    }

    public Land getLandByFarmerIdAndLandId(Integer farmerId, Integer landId) {
        QueryWrapper<Land> wrapper = new QueryWrapper<>();
        wrapper.eq("farmer_id", farmerId)
                .eq("land_id", landId);
        Land land = landMapper.selectOne(wrapper);
        return land;
    }

    public Boolean upgrade(Integer farmerId, Integer landId) {
        Double money = farmerService.getMoney(farmerId);
        Land land = getLandByFarmerIdAndLandId(farmerId, landId);
        Integer beforeType = land.getType();
        int cost = 0;
        int afterType = -1;
        if (Objects.equals(beforeType, Land.TYPE_YELLOW)) { //黄土地升级红土地
            cost = redUpgradeCost;
            afterType = Land.TYPE_RED;
        } else if (Objects.equals(beforeType, Land.TYPE_RED)) {//红土地升级黑土地
            cost = blackUpgradeCost;
            afterType = Land.TYPE_BLACK;
        } else {
            System.err.println("黑土地违法升级！");
        }
        if (money < cost) {
            return false;
        }
        //扣钱
        farmerService.setMoney(farmerId, money - cost);
        //升级
        UpdateWrapper<Land> wrapper = new UpdateWrapper<>();
        wrapper.eq("farmer_id", farmerId)
                .eq("land_id", landId)
                .set("type", afterType);
        landMapper.update(null, wrapper);
        return true;
    }
}
