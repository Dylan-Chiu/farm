package top.newpointer.farm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.newpointer.farm.mapper.SpeciesMapper;
import top.newpointer.farm.state.Plant;
import top.newpointer.farm.pojo.Species;

import java.util.List;

@Service
public class SpeciesService {

    @Autowired
    private SpeciesMapper speciesMapper;

    public Species getSpeciesById(int id) {
        Species species = speciesMapper.selectById(id);
        return species;
    }

    public List<Species> getAllSpecies() {
        QueryWrapper<Species> wrapper = new QueryWrapper<>();
        List<Species> speciesList = speciesMapper.selectList(wrapper);
        return speciesList;
    }

    /**
     * 根据植物列表返回一一对应的物种信息列表
     *
     * @param plants
     * @return
     */
    public Species[] getSpeciesArrayByPlants(Plant[] plants) {
        Species[] speciesArray = new Species[plants.length];
        for (int i = 0; i < plants.length; i++) {
            if(plants[i] == null) {
                continue;
            }
            Integer speciesId = plants[i].getSpeciesId();
            Species species = getSpeciesById(speciesId);
            speciesArray[i] = species;
        }
        return speciesArray;
    }
}
