package com.ecut.test;

import redis.clients.jedis.Jedis;

public class JedisTest {
    public static void main(String[] args) {
        // 1.创建Jedis连接
        String host = "1.117.87.146";
        int port = 6379;
        Jedis jedis = new Jedis(host, port);
        //2.设置访问密码
        jedis.auth("root");
        // 3.检测连通性

        System.out.println("result=" + jedis.get("120152"));
        // 4.关闭Jedis连接
        jedis.close();
    }
}
