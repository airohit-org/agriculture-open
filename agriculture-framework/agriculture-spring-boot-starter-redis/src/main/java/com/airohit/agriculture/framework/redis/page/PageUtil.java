package com.airohit.agriculture.framework.redis.page;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @description: redis分页工具
 * @author: fcy
 * @time: 2022/2/14 13:37
 */
@Component
public class PageUtil {
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 存放单个hash缓存
     *
     * @param key   键
     * @param hkey  键
     * @param value 值
     * @return
     */
    public boolean hput(String key, String hkey, Object value) {
        try {
            redisTemplate.opsForHash().put(key, hkey, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 分页存取数据
     *
     * @param key   hash存取的key
     * @param hkey  hash存取的hkey
     * @param score 指定字段排序
     * @param value
     * @return
     */
    public boolean setPage(String key, String hkey, double score, String value) {
        boolean result = false;
        try {
            result = redisTemplate.opsForZSet().add(key + ":page", hkey, score);
            //result = hput(key, hkey, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置辅助分页的过期时间
        redisTemplate.expire(key + ":page", 1800000, TimeUnit.MILLISECONDS);
        //redisTemplate.expire(key,60000 , TimeUnit.MILLISECONDS);
        return result;
    }

    /**
     * 分页取出 hash中hkey值
     *
     * @param key
     * @param offset
     * @param count
     * @return
     */
    public Set<String> getPage(String key, int offset, int count) {
        Set<String> result = null;
        try {
            result = redisTemplate.opsForZSet().rangeByScore(key + ":page", 1, 100000, (offset - 1) * count, count);//1 100000代表score的排序氛围值，即从1-100000的范围
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 计算key值对应的数量
     *
     * @param key
     * @return
     */
    public Integer getSize(String key) {
        Integer num = 0;
        try {
            Long size = redisTemplate.opsForZSet().zCard(key + ":page");
            return size.intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }
}

