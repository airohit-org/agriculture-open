package com.airohit.agriculture.framework.redis.page;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * @description:
 * @author: zyg
 * @time: 2022/5/01
 */
@Component
public class RedisFurryAndPageQueryUtil<T> {
    /**
     * 插入辅助分页
     */
    @Resource
    private PageUtil pageUtil;
    /**
     * 注入redis模板工具
     */
    @Resource
    private RedisTemplate redisTemplate;


    /**
     * 根据条件进行模糊查询查询并返回分页结果
     *
     * @param name
     * @param currentPage
     * @param count
     * @return
     */
    public PageResult<T> find(String name, int currentPage, int count, String tableName, T t) {
        //将传入的参数变为小写
        name = name.toLowerCase();
        //map用来存储查询到的结果以及分页的总数据
//        Map<String, Object> map = new HashMap<>();
        PageResult<T> page = new PageResult<T>();
        //用来存储查询到的ZhDicGoods
        ArrayList<T> result = new ArrayList<>();
        //定义pageName用来查看redis中是否存在对应的辅助分页key-value
        String pageName = tableName + name + ":page";
        //是否存在对应的辅助分页辅助分页key-value
        Boolean ifExist = redisTemplate.hasKey(pageName);
        //如果不存在，生成辅助分页，同时查询
        if (!ifExist) {
            try {
                Cursor<Map.Entry<String, String>> cursor = null;
                //如果传入参数为空则查询所有
                if (name == null || "".equals(name)) {
                    //模糊查询返回结果
                    //Cusor中存储的是查询key对应的Map
                    cursor = redisTemplate
                            .opsForHash()
                            //绑定模糊查询的hash的key
                            .scan(tableName, ScanOptions.scanOptions()
                                    //模糊查询规则
                                    .match("*")
                                    .count(1000).build());
                } else {

                    //模糊查询返回结果
                    //Cusor中存储的是查询key对应的Map
                    cursor = redisTemplate
                            .opsForHash()
                            //绑定模糊查询的hash的key
                            .scan(tableName, ScanOptions.scanOptions()
                                    //模糊查询规则
                                    .match("*" + name + "*")
                                    .count(10000).build());
                    //生成分页key-value
                    setPage(cursor, name, tableName);
                    //生成查询结果并且放入map中
                    getPageResult(name, currentPage, count, page, result, tableName, t);
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //有辅助分页key-value,直接查询结果放入map中
            getPageResult(name, currentPage, count, page, result, tableName, t);
        }
        //返回result
        return page;

    }


    /**
     * 生成辅助分页
     *
     * @param cursor
     * @param name：会在pageutil中自动加上:page
     */
    private void setPage(Cursor<Map.Entry<String, String>> cursor, String name, String tableName) {
        //setPage初始化值,zset的sort值
        int i = 1;
        //遍历模糊查询结果,将模糊查询的结果生成分页数据，用zset存储模糊查询的数据排序
        //存储名称是查询name+：page，值是hash类型的key
        while (cursor.hasNext()) {
            Map.Entry<String, String> result = cursor.next();
            //获取key
            String key = result.getKey();
            //存储对应的辅助分页数据
            pageUtil.setPage(tableName + name, key, i, null);
            i++;

        }
    }


    /**
     * 获取分页的总数据，并且加入到map中
     *
     * @param name
     * @param currentPage
     * @param count
     * @param page
     * @param result
     */
    private void getPageResult(String name, int currentPage, int count, PageResult<T> page, ArrayList result, String tableName, T t) {

        //得到分页数据总条数
        Integer totalNumber = pageUtil.getSize(tableName + name);
        //得到对应分页的key数组
        Set<String> keyPages = pageUtil.getPage(tableName + name, currentPage, count);
        //遍历循环查询对应cout对应的数据，同时转为对象输入到List
        for (String keyPage : keyPages) {
            //根据zset的key查询hash对应的数据
            T Object = (T) redisTemplate.boundHashOps(tableName).get(keyPage);
            result.add(Object);
        }
        //返回总页数
        //加入返回实体类结果，总条数，起始页数，总条数
        page.setList(result);
        page.setTotal(Long.valueOf(totalNumber));
    }
}


