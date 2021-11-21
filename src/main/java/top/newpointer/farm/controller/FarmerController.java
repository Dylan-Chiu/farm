package top.newpointer.farm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.newpointer.farm.service.FarmerService;

@RestController
@RequestMapping("/farmer")
public class FarmerController {

    @Autowired
    private FarmerService farmerService;

}
