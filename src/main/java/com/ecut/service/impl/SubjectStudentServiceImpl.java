package com.ecut.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecut.common.Redis;
import com.ecut.mapper.SubjectStudentMapper;
import com.ecut.model.SubjectStudentDO;
import com.ecut.service.SubjectStudentService;
import com.ecut.util.RedisUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhouwei
 * @since 2022-06-25
 */
@Service
@Slf4j
public class SubjectStudentServiceImpl extends ServiceImpl<SubjectStudentMapper, SubjectStudentDO> implements SubjectStudentService {
    @Resource
    ObjectMapper objectMapper;
    @Resource
    SubjectStudentMapper subjectStudentMapper;

    @Override
    public List<SubjectStudentDO> allList() throws JsonProcessingException {
        //redis 缓存穿透问题
        List<SubjectStudentDO> list = new ArrayList<>();
        JedisPool pool = RedisUtil.open(Redis.host, Redis.port);
        Jedis jedis = pool.getResource();


        List<String> redisList = jedis.lrange("list", 0, -1);
        log.info("redis的缓存结果:{}", redisList);
        if (redisList != null && !redisList.isEmpty()) {
            for (String s : redisList) {
                SubjectStudentDO sub = objectMapper.readValue(s, SubjectStudentDO.class);
                list.add(sub);
            }
        }
        if (list.isEmpty()) {
            list = subjectStudentMapper.allList();
            if (null == list) {
                jedis.expire("list", 30);
            } else {
                list.forEach(info -> {
                    try {
                        jedis.lpush("list", objectMapper.writeValueAsString(info));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        jedis.close();
        return list;
    }
}
