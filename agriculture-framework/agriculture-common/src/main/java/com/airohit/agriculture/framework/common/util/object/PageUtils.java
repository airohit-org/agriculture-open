package com.airohit.agriculture.framework.common.util.object;

import com.airohit.agriculture.framework.common.pojo.PageParam;

/**
 * {@link com.airohit.agriculture.framework.common.pojo.PageParam} 工具类
 *
 * @author shiminghao
 */
public class PageUtils {

    public static int getStart(PageParam pageParam) {
        return (pageParam.getPageNo() - 1) * pageParam.getPageSize();
    }

}
