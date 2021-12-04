package top.newpointer.farm.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.newpointer.farm.service.BackpackFruitService;
import top.newpointer.farm.service.FarmerService;
import top.newpointer.farm.service.SpeciesService;

import java.util.List;

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

    @Override
    public Double sellList(Integer farmerId, List<Integer> speciesIdList, List<Integer> numberList) {
        //计算收益
        double profit = 0;
        for (int i = 0;i<speciesIdList.size();i++) {
            Integer speciesId = speciesIdList.get(i);
            Integer number = numberList.get(i);
            double oneProfit = speciesService.getSpeciesById(speciesId).getProfit();
            oneProfit *= number;
            profit += oneProfit;
        }
        //修改金币
        Double money = farmerService.getMoney(farmerId);
        farmerService.setMoney(farmerId, money + profit);
        //删除果实
        for (int i = 0; i < speciesIdList.size(); i++) {
            Integer speciesId = speciesIdList.get(i);
            Integer number = numberList.get(i);
            backpackFruitService.alterFruit(farmerId, speciesId, -number);
        }
        return profit;
    }
}
