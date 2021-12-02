package top.newpointer.farm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.newpointer.farm.mapper.BackpackFruitMapper;
import top.newpointer.farm.pojo.BackpackFruit;

import java.util.List;

@Service
public class BackpackFruitService {

    @Autowired
    private BackpackFruitMapper backpackFruitMapper;

    public List<BackpackFruit> getFruitsByFarmerId(Integer farmerId) {
        QueryWrapper<BackpackFruit> wrapper = new QueryWrapper<>();
        wrapper.eq("farmer_id",farmerId);
        List<BackpackFruit> fruits = backpackFruitMapper.selectList(wrapper);
        return fruits;
    }

    public void alterFruit(Integer farmerId, Integer speciesId, Integer delta) {
        //查看数据库中是否有记录
        boolean hasRecord;
        QueryWrapper<BackpackFruit> queryCountWrapper = new QueryWrapper<>();
        queryCountWrapper.eq("farmer_id", farmerId)
                .eq("species_id",speciesId);
        if (backpackFruitMapper.selectCount(queryCountWrapper) == 0) {//数据库中不存在记录
            BackpackFruit backpackFruit = new BackpackFruit(farmerId, speciesId, delta, null);
            backpackFruitMapper.insert(backpackFruit);
        } else {//数据中已存在记录
            Integer beforeNumber = getOneFruitNumber(farmerId, speciesId);
            Integer afterNumber = beforeNumber + delta;
            UpdateWrapper<BackpackFruit> backpackSeedUpdateWrapper = new UpdateWrapper<>();
            backpackSeedUpdateWrapper.set("number", afterNumber)
                    .eq("farmer_id", farmerId)
                    .eq("species_id", speciesId);
            backpackFruitMapper.update(null, backpackSeedUpdateWrapper);
        }
    }

    public Integer getOneFruitNumber(Integer farmerId, Integer speciesId) {
        QueryWrapper<BackpackFruit> wrapper = new QueryWrapper<>();
        wrapper.eq("farmer_id", farmerId)
                .eq("species_id", speciesId);
        BackpackFruit backpackFruit = backpackFruitMapper.selectOne(wrapper);
        if (backpackFruit == null) {
            return 0;
        } else {
            return backpackFruit.getNumber();
        }
    }

}
