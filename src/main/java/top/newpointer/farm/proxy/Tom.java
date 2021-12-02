package top.newpointer.farm.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.newpointer.farm.service.FarmerService;

@Service
public class Tom extends Businessman {

    public final static Integer CODE = 2;

    public static final Double PROXY_MONEY = 10.0;

    @Autowired
    private SystemBuyer systemBuyer;

    @Autowired
    private FarmerService farmerService;

    @Override
    protected Double preSell(Integer farmerId, Integer speciesId, Integer number) {
        Double money = farmerService.getMoney(farmerId);
        farmerService.setMoney(farmerId, money - PROXY_MONEY);
        return -PROXY_MONEY;
    }

    /**
     * 百分之20的概率把收益全收走，百分之80的概率把收益翻倍
     *
     * @param farmerId
     * @param speciesId
     * @param number
     * @param systemMoney
     * @return
     */
    @Override
    protected Double postSell(Integer farmerId, Integer speciesId, Integer number, Double systemMoney) {
        Double beforeMoney = farmerService.getMoney(farmerId);
        Double postMoney;
        if (Math.random() < 0.2) {
            farmerService.setMoney(farmerId, beforeMoney - systemMoney);
            postMoney = -systemMoney;
        } else {
            farmerService.setMoney(farmerId, beforeMoney + systemMoney);
            postMoney = systemMoney;
        }
        return postMoney;
    }

    @Override
    public Double sell(Integer farmerId, Integer speciesId, Integer number) {
        Double preMoney = preSell(farmerId, speciesId, number);
        Double systemMoney = systemBuyer.sell(farmerId, speciesId, number);
        Double postMoney = postSell(farmerId, speciesId, number, systemMoney);
        return preMoney + systemMoney + postMoney;
    }
}
