package top.newpointer.farm.utils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class Message {
    Map<String, Object> map = new HashMap<>();

    public Message() {
        map.put("status", StatusCode.SUCCEED);//默认正常
    }

    public void put(String key, Object value) {
        map.put(key, value);
    }

    public void setState(Integer state) {
        map.put("status", state);
    }

    public String toJSONString() {
        return JSON.toJSONString(map);
    }

    @Override
    public String toString() {
        return this.toJSONString();
    }

}
