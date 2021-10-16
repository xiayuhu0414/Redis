package com.xiayuhu.jedis;

import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * @author xyh
 * @date 2021/10/14 9:32
 */

/*
 * 1.输入手机号，点击发送后随机生成6位数字码。2分钟有效
 * 2.输入验证码，点击验证，返回成功或失败
 * 3.每个手机号码每天只能输入3次
 * */
public class PhoneCode {
    public static void main(String[] args) {
        //模拟验证码发送
        verifyCode("17551704874");
        getRedisCode("17551704874", "123456");
    }

    // 3 验证码校验
    public static void getRedisCode(String phone, String code) {
        //从redis获取验证码
        Jedis jedis = new Jedis("192.168.19.129", 6379);
        //验证码key
        String codeKey = "verifyCode" + phone + ":code";
        String redisCode = jedis.get(codeKey);
        System.out.println(redisCode);
        //判断
        if (redisCode.equals(code)) {
            System.out.println("成功");
        } else {
            System.out.println("失败");
        }
        jedis.close();
    }

    //2 每个手机每天只能发送三次，验证码放到Redis，设置过期时间
    public static void verifyCode(String phone) {
        // 连接Redis
        Jedis jedis = new Jedis("192.168.19.129", 6379);
        //拼接key
        //手机发送次数key
        String countKey = "VerifyCode" + phone + ":count";
        //验证码key
        String codeKey = "verifyCode" + phone + ":code";

        //每个手机每天只能发送三次
        String count = jedis.get(countKey);
        System.out.println(count);
        if (count == null) {
            //没有发送次数，第一次发送
            //设置发送次数是一
            jedis.setex(countKey, 24 * 60 * 60, "1");
        } else if (Integer.parseInt(count) <= 2) {
            //发送次数+1
            jedis.incr(countKey);
        } else if (Integer.parseInt(count) > 2) {
            System.out.println("今天发送次数已经超过三次");
            jedis.close();
            return;
        }
        //发送验证码放到Redis里面
        String vcode = getCode();
        System.out.println(vcode);
        jedis.setex(codeKey, 120, vcode);
        String coun = jedis.get(countKey);
        System.out.println(coun);
        String value=jedis.get(codeKey);
        System.out.println("1"+value);
        jedis.close();
    }

    //1 生成6位数字验证码
    public static String getCode() {
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++) {
            int rand = random.nextInt(10);
            code += rand;
        }
        return code;
    }
}
