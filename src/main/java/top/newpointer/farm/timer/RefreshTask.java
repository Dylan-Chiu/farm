package top.newpointer.farm.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.newpointer.farm.Signleton.PlantSet;

import java.util.Date;

@Component
public class RefreshTask {

    @Scheduled(cron = "* * * * * *")//每秒执行一次
    public void refresh() {
        System.out.println(new Date());
        //更新rest_time
        PlantSet.getInstance().updateRestTime();
        //更新数据库
        PlantSet.getInstance().updatePlantsIntoDatabase();
    }
}
