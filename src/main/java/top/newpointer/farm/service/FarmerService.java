package top.newpointer.farm.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.newpointer.farm.mapper.FarmerMapper;
import top.newpointer.farm.mapper.SpeciesMapper;
import top.newpointer.farm.pojo.Farmer;

@Service
public class FarmerService {

    @Autowired
    private FarmerMapper farmerMapper;

    @Value("${experienceLength}")
    private Integer experienceLength = 200;

    public Farmer getFarmerById(Integer farmerId) {
        Farmer farmer = farmerMapper.selectById(farmerId);
        farmer.setPassword(null);
        //更新经验框大小
        Integer exp = farmer.getExperience();
        Integer level = getLevelByExperience(exp);
        farmer.setCurrentExpLen(getCurrentExpLenByLevel(level));
        return farmer;
    }

    public Double getMoney(Integer farmerId) {
        Farmer farmer = farmerMapper.selectById(farmerId);
        return farmer.getMoney();
    }

    public void setMoney(Integer farmerId, Double money) {
        UpdateWrapper wrapper = new UpdateWrapper<Farmer>();
        wrapper.eq("id", farmerId);
        wrapper.set("money", money);
        farmerMapper.update(null, wrapper);
    }

    public void setExperience(Integer farmerId, Integer experience) {
        UpdateWrapper wrapper = new UpdateWrapper<Farmer>();
        wrapper.eq("id", farmerId);
        wrapper.set("experience", experience);
        farmerMapper.update(null, wrapper);
    }

    public Integer getLevelByExperience(Integer experience) {
        int level = -1;
        while (experience >= 0) {
            level++;
            experience -= experienceLength * level;
        }
        return level;
    }

    /**
     * 查看当前段的经验
     *
     * @return
     */
    public Integer getCurrentExpLenByLevel(Integer level) {
        int cnt = 0;
        for (Integer i = 0; i < level; i++) {
            cnt += experienceLength * (i + 1);
        }
        return cnt;
    }

    public void updateLevelAndExpLen(Farmer farmer) {
        int exp = farmer.getExperience();
        int level = getLevelByExperience(exp);
        int expLen = getCurrentExpLenByLevel(level);
        farmer.setLevel(level);
        farmer.setCurrentExpLen(expLen);
    }

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            int x = getCurrentExpLenByLevel(i);
            System.out.println(x);
        }
    }

    @Test
    public void test1() {
        for (int i = 0; i < 600; i++) {
            int x = getLevelByExperience(i);
            System.out.println(i + "  " + x);
        }
    }
}
