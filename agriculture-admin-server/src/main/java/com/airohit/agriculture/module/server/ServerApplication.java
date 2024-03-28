package com.airohit.agriculture.module.server;

import com.dtflys.forest.springboot.annotation.ForestScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 项目的启动类
 *
 * @author shiminghao
 */
@SpringBootApplication(scanBasePackages = {"com.airohit.agriculture.module.**","com.airohit.agriculture.module.system"})
@MapperScan("com.airohit.agriculture.module.**.mysql")
@EnableAsync
@EnableScheduling
@ForestScan({"com.airohit.agriculture.module.device.client.obs","com.airohit.agriculture.module.device.client.BeijingTH"})
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
