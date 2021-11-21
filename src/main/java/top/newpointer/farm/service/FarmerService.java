package top.newpointer.farm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.newpointer.farm.mapper.FarmerMapper;
import top.newpointer.farm.mapper.SpeciesMapper;
import top.newpointer.farm.pojo.Farmer;

@Service
public class FarmerService {

    @Autowired
    private FarmerMapper farmerMapper;

    public Double getMoney(Integer farmerId) {
        Farmer farmer = farmerMapper.selectById(farmerId);
        return farmer.getMoney();
    }

    public void setMoney(Integer farmerId, Double money) {
        Farmer farmer = new Farmer();
        farmer.setId(farmerId);
        farmer.setMoney(money);
        farmerMapper.updateById(farmer);
    }

}
