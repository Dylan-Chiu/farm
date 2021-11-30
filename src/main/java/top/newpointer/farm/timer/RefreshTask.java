package top.newpointer.farm.timer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.newpointer.farm.Signleton.PlantSet;
import top.newpointer.farm.state.Plant;

import java.util.Date;

@Component
public class RefreshTask {

    @Value("${dryProbability}")
    private Double waterProbability;

    @Scheduled(cron = "* * * * * *")//每秒执行一次
    public void refresh() {
        System.out.println(new Date());
        //更新rest_time
        PlantSet.getInstance().updateRestTime();
        //随机缺水状态
        PlantSet.getInstance().beNeedWaterAtProbability(waterProbability);
        //更新濒死状态
        PlantSet.getInstance().updateDying();
        //更新数据库
        PlantSet.getInstance().updatePlantsIntoDatabase();
    }
}
