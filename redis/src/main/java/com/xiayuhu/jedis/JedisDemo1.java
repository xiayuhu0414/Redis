package com.xiayuhu.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * @author xyh
 * @date 2021/10/12 8:17
 */
public class JedisDemo1 {
    public static void main(String[] args) {
        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.19.129", 6379);
        //测试
        String value = jedis.ping();
        System.out.println(value);

    }

    //操作key
    @Test
    public void demo1() {
        Jedis jedis = new Jedis("192.168.19.129", 6379);

        jedis.set("name", "夏玉虎");
        String name = jedis.get("name");
        System.out.println(name);
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }

    }

    //操作List
    @Test
    public void demo2() {
        Jedis jedis = new Jedis("192.168.19.129", 6379);
        jedis.lpush("key1", "xiayuhu", "gr");
        List<String> values = jedis.lrange("key1", 0, -1);
        System.out.println(values);

    }

    //操作set
    @Test
    public void demo3() {
        Jedis jedis = new Jedis("192.168.19.129", 6379);
        jedis.sadd("names", "rr", "gg");
        Set<String> names = jedis.smembers("names");
        System.out.println(names);

    }

    //操作hash
    @Test
    public void demo4() {
        Jedis jedis = new Jedis("192.168.19.129", 6379);
        jedis.hset("users", "rr", "20");
        String hget = jedis.hget("users", "rr");
        System.out.println(hget);

    }

    //操作zset
    @Test
    public void demo5() {
        Jedis jedis = new Jedis("192.168.19.129", 6379);
        jedis.zadd("rr", 20, "gg");
        Set<String> rr = jedis.zrange("rr", 0, -1);
        System.out.println(rr);

    }
}

