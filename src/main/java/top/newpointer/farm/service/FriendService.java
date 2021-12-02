package top.newpointer.farm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.newpointer.farm.mapper.FriendMapper;
import top.newpointer.farm.pojo.Friend;

@Service
public class FriendService {

    @Autowired
    private FriendMapper friendMapper;

    public void ApplyForFriend(Integer farmerId, Integer friendId) {
        friendMapper.insert(new Friend(farmerId, friendId));
    }

    public void getFriends(Integer farmerId) {

    }
}
