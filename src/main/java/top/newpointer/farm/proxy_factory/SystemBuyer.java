package top.newpointer.farm.proxy_factory;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.newpointer.farm.mapper.BackpackFruitMapper;
import top.newpointer.farm.mapper.FarmerMapper;
import top.newpointer.farm.pojo.BackpackFruit;
import top.newpointer.farm.service.BackpackFruitService;
import top.newpointer.farm.service.FarmerService;
import top.newpointer.farm.service.SpeciesService;

@Service
public class SystemBuyer implements Buyer {

    public final static Integer CODE = 0;

    @Autowired
    private BackpackFruitService backpackFruitService;

    @Autowired
    private FarmerService farmerService;

    @Autowired
    private SpeciesService speciesService;

    @Override
    public Double sell(Integer farmerId, Integer speciesId, Integer number) {
        //增加用户金钱数
        double profit = speciesService.getSpeciesById(speciesId).getProfit();
        double delta = profit * number;
        Double money = farmerService.getMoney(farmerId);
        farmerService.setMoney(farmerId, money + delta);
        //删除背包果实数
        backpackFruitService.alterFruit(farmerId, speciesId, -number);
        //返回增加的金钱数
        return delta;
    }
}
