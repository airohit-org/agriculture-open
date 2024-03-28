package com.airohit.agriculture.module.device.vo.farmyun.worm;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/6 13:35
 */
public class WormStatisticsByGroupResponseVo {


    /**
     * deviceAddr : 20000009
     * wornData : [{"num":1,"name":"飞蚂蚁"},{"num":2,"name":"白蛾"},{"num":2,"name":"飞虱"},{"num":34,"name":"蜱虫"},{"num":10,"name":"金龟子"},{"num":1,"name":"螟蛾"},{"num":4,"name":"硬壳虫"},{"num":1,"name":"隐翅虫"},{"num":7,"name":"射炮步甲"},{"num":7,"name":"蠓虫"}]
     */

    private String deviceAddr;
    private List<WornData> wornData;

    public String getDeviceAddr() {
        return deviceAddr;
    }

    public void setDeviceAddr(String deviceAddr) {
        this.deviceAddr = deviceAddr;
    }

    public List<WornData> getWornData() {
        return wornData;
    }

    public void setWornData(List<WornData> wornData) {
        this.wornData = wornData;
    }

    public static class WornData {
        /**
         * num : 1
         * name : 飞蚂蚁
         */

        private int num;
        private String name;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
