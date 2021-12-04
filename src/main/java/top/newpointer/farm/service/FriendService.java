package top.newpointer.farm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.newpointer.farm.mapper.FriendMapper;
import top.newpointer.farm.pojo.Farmer;
import top.newpointer.farm.pojo.Friend;
import top.newpointer.farm.utils.StatusCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class FriendService {

    @Autowired
    private FriendMapper friendMapper;

    @Autowired
    private FarmerService farmerService;

    public Integer applyForFriend(Integer farmerId, Integer friendId) {
        //查看是否已经申请过了
        Boolean hasForward = hasForward(farmerId, friendId);
        if(hasForward) {
            return StatusCode.REPEAT_APPLY;
        }
        friendMapper.insert(new Friend(farmerId, friendId));
        return StatusCode.SUCCEED;
    }

    /**
     * 获取好友申请列表，即friend_id为自己，但是，自己没有在对应的farmer_id里
     *
     * @param farmerId
     */
    public List<Farmer> getFriendApplicationList(Integer farmerId) {
        //查看friend_id为我的记录
        QueryWrapper<Friend> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("friend_id", farmerId);
        List<Friend> list1 = friendMapper.selectList(wrapper1);

        //查看farmer_id为我的记录
        QueryWrapper<Friend> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("farmer_id", farmerId);
        List<Friend> list2 = friendMapper.selectList(wrapper2);

        //从list1里删除掉list2中存在的记录
        //friend_id为我的记录，并且不存在相反关系的记录
        for (Friend friend : list2) {
            list1.remove(friend);
        }

        ArrayList<Farmer> friendFarmers = new ArrayList<>();
        for (Friend friend : list1) {
            Integer id = friend.getFarmerId();
            Farmer farmer = farmerService.getFarmerById(id);
            friendFarmers.add(farmer);
        }
        return friendFarmers;
    }

    /**
     * 同意好友邀请
     * 写入一条反向数据
     */
    public Integer agreeFriend(Integer farmerId, Integer friendId) {
        //检查是否有收到好友邀请
        Boolean hasReverse = hasReverse(farmerId, friendId);
        if (hasReverse == false) {
            return StatusCode.NO_FRIEND_INVITATION;
        }
        //检测是否已成为好友（检查正向关系）
        Boolean hasForward = hasForward(farmerId, friendId);
        if (hasForward == true) {
            return StatusCode.ALREADY_FRIEND;
        }
        friendMapper.insert(new Friend(farmerId, friendId));
        return StatusCode.SUCCEED;
    }

    /**
     * 获取好友列表
     *
     * @return
     */
    public List<Farmer> getFriends(Integer farmerId) {
        //查看friend_id为我的记录
        QueryWrapper<Friend> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("friend_id", farmerId);
        List<Friend> list1 = friendMapper.selectList(wrapper1);
        //交换两个字段
        for (Friend friend : list1) {
            Integer temp = friend.getFriendId();
            friend.setFriendId(friend.getFarmerId());
            friend.setFarmerId(temp);
        }
        //查看farmer_id为我的记录
        QueryWrapper<Friend> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("farmer_id", farmerId);
        List<Friend> list2 = friendMapper.selectList(wrapper2);

        //查看交叠部分
        ArrayList<Farmer> friendFarmers = new ArrayList<>();
        for (Friend friend : list2) {
            if (list1.contains(friend)) {
                Integer id = friend.getFriendId();
                Farmer farmer = farmerService.getFarmerById(id);
                friendFarmers.add(farmer);
            }
        }
        return friendFarmers;
    }

    /**
     * 是否为好友关系（双向关系）
     *
     * @param farmerId
     * @param friendId
     * @return
     */
    public Boolean isFriend(Integer farmerId, Integer friendId) {
        return hasForward(farmerId, friendId) && hasReverse(farmerId, friendId);
    }

    /**
     * 是否存在正向关系
     *
     * @return
     */
    public Boolean hasForward(Integer farmerId, Integer friendId) {
        QueryWrapper<Friend> wrapper = new QueryWrapper<>();
        wrapper.eq("farmer_id", farmerId)
                .eq("friend_id", friendId);
        Friend relation = friendMapper.selectOne(wrapper);
        if (relation == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 是否存在反向关系
     */
    public Boolean hasReverse(Integer farmerId, Integer friendId) {
        return hasForward(friendId, farmerId);
    }

    public Integer rejectFriend(Integer farmerId, Integer friendId) {
        Boolean hasReverse = hasReverse(farmerId, friendId);
        if(hasReverse == false) {
            return StatusCode.NO_FRIEND_INVITATION;
        }
        //删除friend->farmer的关系
        Friend friend = new Friend(friendId, farmerId);
        QueryWrapper<Friend> wrapper = new QueryWrapper<>(friend);
        friendMapper.delete(wrapper);
        return StatusCode.SUCCEED;
    }

    public Integer deleteFriend(Integer farmerId, Integer friendId) {
        Boolean isFriend = isFriend(farmerId, friendId);
        if(isFriend == false) {
            return StatusCode.NOT_FRIEND;
        }
        //删除friend->farmer的关系
        Friend r1 = new Friend(friendId, farmerId);
        QueryWrapper<Friend> wrapper1 = new QueryWrapper<>(r1);
        friendMapper.delete(wrapper1);
        //删除farmer->friend关系
        Friend r2 = new Friend(farmerId, friendId);
        QueryWrapper<Friend> wrapper2 = new QueryWrapper<>(r2);
        friendMapper.delete(wrapper2);
        return StatusCode.SUCCEED;
    }
}
