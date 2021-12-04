package top.newpointer.farm.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.newpointer.farm.service.FarmerService;

import java.util.List;

@Service
public class Jack extends Businessman {

    public final static Integer CODE = 1;

    public static final Double PROXY_MONEY = 50.0;

    @Autowired
    private SystemBuyer systemBuyer;

    @Autowired
    private FarmerService farmerService;

    /**
     * 固定收取代理费PROXY_MONEY
     * @param farmerId
     * @param speciesId
     * @param number
     * @return
     */
    @Override
    protected Double preSell(Integer farmerId, Integer speciesId, Integer number) {
        Double money = farmerService.getMoney(farmerId);
        farmerService.setMoney(farmerId,money - PROXY_MONEY);
        return -PROXY_MONEY;
    }

    /**
     * 概率增加 0% - 50% 的金钱
     * @param farmerId
     * @param speciesId
     * @param number
     * @return
     */
    @Override
    protected Double postSell(Integer farmerId, Integer speciesId, Integer number, Double systemMoney) {
        double anotherMoney = Math.round(Math.random() * systemMoney * 0.5);
        Double money = farmerService.getMoney(farmerId);
        farmerService.setMoney(farmerId,money + anotherMoney);
        return anotherMoney;
    }

    @Override
    public Double sell(Integer farmerId, Integer speciesId, Integer number) {
        Double preMoney = preSell(farmerId, speciesId, number);
        Double systemMoney = systemBuyer.sell(farmerId, speciesId, number);
        Double postMoney = postSell(farmerId, speciesId, number, systemMoney);
        return preMoney + systemMoney + postMoney;
    }

    @Override
    public Double sellList(Integer farmerId, List<Integer> speciesIdList, List<Integer> numberList) {
        Double preMoney = preSell(farmerId, null,null);
        Double systemMoney = systemBuyer.sellList(farmerId, speciesIdList, numberList);
        Double postMoney = postSell(farmerId, null, null,systemMoney);
        return preMoney + systemMoney + postMoney;
    }
}
