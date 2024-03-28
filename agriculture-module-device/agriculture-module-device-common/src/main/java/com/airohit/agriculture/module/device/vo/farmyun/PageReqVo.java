package com.airohit.agriculture.module.device.vo.farmyun;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 15:51
 */
@Data
public class PageReqVo {

    private int pages;
    /**
     * 最大1000
     */
    private int limit;

}
