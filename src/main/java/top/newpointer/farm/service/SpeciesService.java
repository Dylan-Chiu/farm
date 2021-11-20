package top.newpointer.farm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.newpointer.farm.mapper.SpeciesMapper;
import top.newpointer.farm.state.Plant;
import top.newpointer.farm.pojo.Species;

@Service
public class SpeciesService {

    @Autowired
    private SpeciesMapper speciesMapper;

    public Species getSpeciesById(int id) {
        Species species = speciesMapper.selectById(id);
        return species;
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
