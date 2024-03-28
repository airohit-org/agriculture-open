package com.airohit.agriculture.module.system.api.slave.vo;


import lombok.Data;

import java.time.LocalDateTime;


/**
 * 租户 Excel VO
 *
 * @author shiminghao
 */
@Data
public class TenantExcelVO {

    private Long id;

    private String name;

    private String contactName;

    private String contactMobile;


    private Integer status;

    private LocalDateTime createTime;

}
