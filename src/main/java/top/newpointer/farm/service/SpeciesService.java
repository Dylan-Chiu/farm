package top.newpointer.farm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.newpointer.farm.Signleton.PlantSet;
import top.newpointer.farm.mapper.SpeciesMapper;
import top.newpointer.farm.pojo.Species;

@Service
public class SpeciesService {

    @Autowired
    private SpeciesMapper speciesMapper;

    public Species getSpeciesById(int id) {
        Species species = speciesMapper.selectById(id);
        return species;
    }
}
