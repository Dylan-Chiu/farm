package top.newpointer.farm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.newpointer.farm.mapper.InteractLogMapper;
import top.newpointer.farm.pojo.InteractLog;

import java.util.Date;
import java.util.List;

@Service
public class InteractLogService {

    @Autowired
    private InteractLogMapper interactLogMapper;

    public List<InteractLog> getLogsByFarmId(Integer farmerId) {
        QueryWrapper<InteractLog> wrapper = new QueryWrapper<>();
        wrapper.eq("friend_id", farmerId);//找的是对我进行操作的记录
        List<InteractLog> logList = interactLogMapper.selectList(wrapper);
        return logList;
    }

    /**
     * @param farmerId 偷盗者
     * @param friendId 被偷盗者
     * @param number   偷盗的数量
     */
    public void recordSteal(Integer farmerId, Integer friendId, Integer speciesId, Integer number) {
        InteractLog log = new InteractLog(null, farmerId, friendId, speciesId, number, InteractLog.STEAL,new Date());
        interactLogMapper.insert(log);
    }

    /**
     * @param farmerId 浇水者
     * @param friendId 被浇水者
     */
    public void recordWater(Integer farmerId, Integer friendId) {
        InteractLog log = new InteractLog(null, farmerId, friendId, 0, 0, InteractLog.WATER, new Date());
        interactLogMapper.insert(log);
    }

    public void deleteById(Integer id) {
        interactLogMapper.deleteById(id);
    }
}
