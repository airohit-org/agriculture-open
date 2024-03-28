package com.airohit.agriculture.module.device.vo.farmyun;

import lombok.Data;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 15:55
 */
@Data
public class PageResVo<T> {
    private int pages;
    /**
     * 最大1000
     */
    private int limit;
    private int totalPages;
    private int total;
    private List<T> rows;
}
