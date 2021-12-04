package top.newpointer.farm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.newpointer.farm.mapper.BackpackFruitMapper;
import top.newpointer.farm.pojo.BackpackFruit;
import top.newpointer.farm.proxy.Jack;
import top.newpointer.farm.proxy.SystemBuyer;
import top.newpointer.farm.proxy.Tom;
import top.newpointer.farm.utils.Message;
import top.newpointer.farm.utils.StatusCode;

import java.util.List;

@Service
public class BackpackFruitService {

    @Autowired
    private BackpackFruitMapper backpackFruitMapper;

    @Autowired
    private BackpackFruitService backpackFruitService;

    @Autowired
    private FarmerService farmerService;

    @Autowired
    private SystemBuyer systemBuyer;

    @Autowired
    private Jack jack;

    @Autowired
    private Tom tom;

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

    public Message sellFruit(Integer farmerId,Integer speciesId,Integer number, int code) {
        Message message = new Message();
        //判断背包数量是否足够
        Integer beforeFruitNumber = backpackFruitService.getOneFruitNumber(farmerId, speciesId);
        if (beforeFruitNumber < number) {
            message.setState(StatusCode.NUMBER_NOT_ENOUGH);
            return message;
        }
        //售卖
        Double beforeMoney = farmerService.getMoney(farmerId);
        Double money;
        if (code == SystemBuyer.CODE) {
            money = systemBuyer.sell(farmerId, speciesId, number);
            message.put("money",money);
        } else if(code == Jack.CODE) {
            if(beforeMoney < Jack.PROXY_MONEY) {//判断是否够代理费
                message.setState(StatusCode.MONEY_NOT_ENOUGH);
                return message;
            }
            money = jack.sell(farmerId, speciesId, number);
            message.put("money",money);
        } else if( code == Tom.CODE) {
            if(beforeMoney < Tom.PROXY_MONEY) {
                message.setState(StatusCode.MONEY_NOT_ENOUGH);
                return message;
            }
            money = tom.sell(farmerId, speciesId, number);
            message.put("money",money);
        } else {
            message.setState(StatusCode.BUYER_ERROR);
        }
        return message;
    }

    public Message sellFruitList(Integer farmerId,List<Integer> speciesIdList,List<Integer> numberList, int code) {
        Message message = new Message();
        //判断背包数量是否足够
        for (int i = 0; i < speciesIdList.size(); i++) {
            Integer speciesId = speciesIdList.get(i);
            Integer number = numberList.get(i);

            Integer beforeFruitNumber = backpackFruitService.getOneFruitNumber(farmerId, speciesId);
            if (beforeFruitNumber < number) {
                message.setState(StatusCode.NUMBER_NOT_ENOUGH);
                return message;
            }
        }
        //售卖
        Double beforeMoney = farmerService.getMoney(farmerId);
        Double money;
        if (code == SystemBuyer.CODE) {
            money = systemBuyer.sellList(farmerId, speciesIdList, numberList);
            message.put("money",money);
        } else if(code == Jack.CODE) {
            if(beforeMoney < Jack.PROXY_MONEY) {//判断是否够代理费
                message.setState(StatusCode.MONEY_NOT_ENOUGH);
                return message;
            }
            money = jack.sellList(farmerId, speciesIdList, numberList);
            message.put("money",money);
        } else if( code == Tom.CODE) {
            if(beforeMoney < Tom.PROXY_MONEY) {
                message.setState(StatusCode.MONEY_NOT_ENOUGH);
                return message;
            }
            money = tom.sellList(farmerId, speciesIdList, numberList);
            message.put("money",money);
        } else {
            message.setState(StatusCode.BUYER_ERROR);
        }
        return message;
    }
}
