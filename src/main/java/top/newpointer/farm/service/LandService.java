package top.newpointer.farm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.newpointer.farm.mapper.LandMapper;

@Service
public class LandService {

    @Autowired
    private LandMapper landMapper;

}
