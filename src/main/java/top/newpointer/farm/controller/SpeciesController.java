package top.newpointer.farm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.newpointer.farm.pojo.Species;
import top.newpointer.farm.service.SpeciesService;
import top.newpointer.farm.utils.Message;

@RestController
@RequestMapping("/species")
public class SpeciesController {

    @Autowired
    private SpeciesService speciesService;

    @RequestMapping("/getSpeciesById")
    public String getSpeciesById(@RequestParam("id") Integer id) {
        Message message = new Message();
        Species species = speciesService.getSpeciesById(id);
        message.put("species",species);
        return message.toString();
    }
}
