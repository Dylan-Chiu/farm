package top.newpointer.farm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.newpointer.farm.pojo.InteractLog;
import top.newpointer.farm.service.InteractLogService;
import top.newpointer.farm.utils.Message;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/interactLog")
public class InteractLogController {

    @Autowired
    private InteractLogService interactLogService;

    @RequestMapping("/getLogList")
    public String getLogList(HttpServletRequest request) {
        Message message = new Message();
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        List<InteractLog> logList = interactLogService.getLogsByFarmId(farmerId);
        message.put("logList", logList);
        return message.toString();
    }

    @RequestMapping("/deleteById")
    public String deleteById(@RequestParam("id") Integer id) {
        Message message = new Message();
        interactLogService.deleteById(id);
        return message.toString();
    }
}
