package top.newpointer.farm.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.newpointer.farm.pojo.InteractLog;
import top.newpointer.farm.service.FarmerService;
import top.newpointer.farm.service.InteractLogService;
import top.newpointer.farm.utils.Message;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/interactLog")
public class InteractLogController {

    @Autowired
    private InteractLogService interactLogService;

    @Autowired
    private FarmerService farmerService;

    @RequestMapping("/getLogList")
    public String getLogList(HttpServletRequest request) {
        Message message = new Message();
        Integer farmerId = (Integer) request.getSession().getAttribute("farmerId");
        //查找friendId为自己的记录信息
        List<InteractLog> logList = interactLogService.getLogsByFarmId(farmerId);
        //清除秒信息
        for (InteractLog interactLog : logList) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(interactLog.getTime());
            instance.set(Calendar.SECOND, 0);
            interactLog.setTime(instance.getTime());
        }
        //根据时间分组
        Map<Date, List<InteractLog>> collect = logList.stream().collect(Collectors.groupingBy(InteractLog::getTime));
        //手动序列化日期
        HashMap<String, List<InteractLog>> logMapList = new HashMap<>();
        for (Date date : collect.keySet()) {
            String strDateFormat = "yyyy-MM-dd HH:mm";
            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
            String format = sdf.format(date);
            logMapList.put(format, collect.get(date));
        }
        //根据时间中的农民分组
        HashMap<String, Map<String, List<InteractLog>>> map = new HashMap<>();
        for (String date : logMapList.keySet()) {
            List<InteractLog> logs = logMapList.get(date);
            Map<Integer, List<InteractLog>> sortByFarmer = logs.stream().collect(Collectors.groupingBy(InteractLog::getFarmerId));
            Map<String, List<InteractLog>> sortByFarmerString = new HashMap<>();
            for (Integer farmerIdInt : sortByFarmer.keySet()) {
                String nickname = farmerService.getFarmerById(farmerIdInt).getNickname();
                sortByFarmerString.put(nickname,sortByFarmer.get(farmerIdInt));
            }
            map.put(date, sortByFarmerString);
        }
        message.put("logList", map);
        return message.toString();
    }

    @RequestMapping("/deleteById")
    public String deleteById(@RequestParam("id") Integer id) {
        Message message = new Message();
        interactLogService.deleteById(id);
        return message.toString();
    }
}
