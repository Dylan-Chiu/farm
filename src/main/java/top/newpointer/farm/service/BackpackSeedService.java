package top.newpointer.farm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.newpointer.farm.mapper.BackpackSeedMapper;
import top.newpointer.farm.pojo.BackpackSeed;
import top.newpointer.farm.pojo.Species;

import java.util.List;

@Service
public class BackpackSeedService {

    @Autowired
    private BackpackSeedMapper backpackSeedMapper;

    @Autowired
    private SpeciesService speciesService;

    @Autowired
    private FarmerService farmerService;

    /**
     * 查询FarmId对应农民背包中的各种种子数量
     *
     * @param farmId
     * @return
     */
    public List<BackpackSeed> getSeedsNumberByFarmerId(Integer farmId) {
        QueryWrapper<BackpackSeed> wrapper = new QueryWrapper<>();
        wrapper.eq("farmer_id", farmId);
        List<BackpackSeed> seedsNumber = backpackSeedMapper.selectList(wrapper);
        for (BackpackSeed backpackSeed : seedsNumber) {
            Integer speciesId = backpackSeed.getSpeciesId();
            Species species = speciesService.getSpeciesById(speciesId);
            backpackSeed.setSpecies(species);
        }
        return seedsNumber;
    }

    /**
     * 给FarmId对应农民背包中改变种子
     *
     * @param farmerId
     * @param speciesId
     * @param delta     改变的数量，可为正可为负
     */
    public void alterSeeds(Integer farmerId, Integer speciesId, Integer delta) {
        //查看数据库中是否有记录
        boolean hasRecord;
        QueryWrapper<BackpackSeed> queryCountWrapper = new QueryWrapper<>();
        queryCountWrapper.eq("farmer_id", farmerId)
                .eq("species_id",speciesId);
        if (backpackSeedMapper.selectCount(queryCountWrapper) == 0) {//数据库中不存在记录
            BackpackSeed backpackSeed = new BackpackSeed(farmerId, speciesId, delta,null);
            backpackSeedMapper.insert(backpackSeed);
        } else {//数据中已存在记录
            Integer beforeNumber = getOneSeedNumber(farmerId, speciesId);
            Integer afterNumber = beforeNumber + delta;
            UpdateWrapper<BackpackSeed> backpackSeedUpdateWrapper = new UpdateWrapper<>();
            backpackSeedUpdateWrapper.set("number", afterNumber)
                    .eq("farmer_id", farmerId)
                    .eq("species_id", speciesId);
            backpackSeedMapper.update(null, backpackSeedUpdateWrapper);
        }
    }

    /**
     * 根据farmerId和speciesId获取单个植物数目
     *
     * @param farmerId
     * @param speciesId
     * @return
     */
    public Integer getOneSeedNumber(Integer farmerId, Integer speciesId) {
        QueryWrapper<BackpackSeed> wrapper = new QueryWrapper<>();
        wrapper.eq("farmer_id", farmerId)
                .eq("species_id", speciesId);
        BackpackSeed backpackSeed = backpackSeedMapper.selectOne(wrapper);
        if (backpackSeed == null) {
            return 0;
        } else {
            return backpackSeed.getNumber();
        }
    }

    /**
     * 购买种子
     *
     * @param farmerId  农民Id
     * @param speciesId 物种Id
     * @param number    购买数量
     * @Return 是否购买成功
     */
    @Transactional
    public Boolean buySeeds(Integer farmerId, Integer speciesId, Integer number) {
        //判断余额是否足够
        Double seedPrice = speciesService.getSpeciesById(speciesId).getSeedPrice();
        Double needMoney = seedPrice * number;
        Double hasMoney = farmerService.getMoney(farmerId);
        if (needMoney > hasMoney) { //余额不足
            return false;
        }
        //修改余额
        farmerService.setMoney(farmerId, hasMoney - needMoney);
        //修改种子数
        alterSeeds(farmerId, speciesId, number);
        return true;
    }
}
