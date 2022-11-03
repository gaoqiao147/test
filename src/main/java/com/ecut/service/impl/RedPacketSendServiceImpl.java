package com.ecut.service.impl;

import com.ecut.common.Redis;
import com.ecut.mapper.RedPacketSendMapper;
import com.ecut.model.RedPacketRecord;
import com.ecut.model.RedPacketSend;
import com.ecut.service.RedPacketRecordService;
import com.ecut.service.RedPacketSendService;
import com.ecut.util.RedPacketUtil;
import com.ecut.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zhouwei
 */
@Service
@Slf4j
public class RedPacketSendServiceImpl implements RedPacketSendService {
    @Resource
    RedPacketRecordService redPacketRecordService;

    @Override
    public HashMap<String, Object> redPacketSend(RedPacketSend redPacketSend) {
        HashMap<String,Object> hashMap = new HashMap<>(4);
        //生成redis连接池
        JedisPool pool = RedisUtil.open(Redis.host, Redis.port);
        Jedis jedis = pool.getResource();

        String id = UUID.randomUUID().toString();
        redPacketSend.setId(id);
        //分红包算法
        List<Integer> integers = RedPacketUtil.divideRedPackage(redPacketSend.getMoney().intValue(), redPacketSend.getNumber());
        //将分好的红包存入redis,存入list
        List<RedPacketRecord> list = new ArrayList<>();
        integers.forEach(info ->{
            jedis.lpush(redPacketSend.getId(), info.toString());
            RedPacketRecord redPacketRecord = new RedPacketRecord();
            redPacketRecord.setRedId(redPacketSend.getId())
                    .setAmount(info.doubleValue())
                    .setCreateTime(new Date())
                    .setId(UUID.randomUUID().toString());
            list.add(redPacketRecord);
        });
        //将红包的总额存入redis
        String key = "sender:" + redPacketSend.getUserId();
        jedis.set(key,redPacketSend.getNumber().toString());
        //存入DB
        int res = redPacketRecordService.insert(redPacketSend,list);
        if( res > 0){
            hashMap.put("redPacketNumber",key);
            hashMap.put("redPacketId",redPacketSend.getId());
        }else {
            hashMap.put("message","失败");
        }
        jedis.close();
        return hashMap;
    }
}
