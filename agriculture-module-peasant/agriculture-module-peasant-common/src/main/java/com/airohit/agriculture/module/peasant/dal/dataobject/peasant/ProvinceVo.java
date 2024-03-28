package com.airohit.agriculture.module.peasant.dal.dataobject.peasant;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigInteger;

@TableName("province")
@KeySequence("province_seq")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceVo implements Serializable {

    /**
     * id
     */
    private BigInteger id;

    /**
     * 省编号
     */
    private String code;

    /**
     *
     */
    private String name;

    private BigInteger tenantId;


}
