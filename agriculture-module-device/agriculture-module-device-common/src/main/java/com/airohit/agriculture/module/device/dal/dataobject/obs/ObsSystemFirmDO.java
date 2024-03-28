package com.airohit.agriculture.module.device.dal.dataobject.obs;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Created with IDEA
 *
 * @author :hanliyao
 * @date :2023-7-19 09:19:32
 */
@TableName("obs_system_firm")
@KeySequence("obs_system_firm_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObsSystemFirmDO extends BaseDO {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 厂商名称
     */
    private String firmName;

}
