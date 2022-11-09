package com.ecut.service.impl;

import com.ecut.common.Redis;
import com.ecut.model.RedPacketRob;
import com.ecut.service.RedPacketRobService;
import com.ecut.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;

/**
 * @author zhouwei
 */
@Service
@Slf4j
public class RedPacketRobServiceImpl implements RedPacketRobService {
    @Override
    public HashMap<String, Object> robRedPacket(RedPacketRob redPacketRob) {
        HashMap<String, Object> map = new HashMap<>(4);
        JedisPool jedisPool = RedisUtil.open(Redis.host, Redis.port);
        Jedis jedis = jedisPool.getResource();
        //通过redis查询该用户是否已经有红包
        String userKey = "userId" + redPacketRob.getUserId();
        String user = jedis.get(userKey);
        //存在
        if (!StringUtils.isEmpty(user)) {
            map.put("message", "该用户已有红包！");
        }   //不存在
            else {
            //从redis根据取出红包，根据redis，更新数据库
            String key = redPacketRob.getRedId();
            String amount = jedis.lpop(key);
            //redis红包数量不为空
            if (!StringUtils.isEmpty(amount)) {
                //获取当前红包数量 并减1
                String currentNum = !StringUtils.isEmpty(jedis.get("sender:" + redPacketRob.getSendUserId())) ? jedis.get("sender:" + redPacketRob.getSendUserId()) : "0";
                int total = Integer.parseInt(currentNum) - 1;
                jedis.set("sender:" + redPacketRob.getSendUserId(), Integer.toString(total));
                //存放已经抢到红包的userId
                jedis.set(userKey, amount);
                //返回结果
                map.put(redPacketRob.getUserId(), amount);
            } else {
                map.put("message", "红包被抢完了");
            }
        }
        return map;
    }
}
