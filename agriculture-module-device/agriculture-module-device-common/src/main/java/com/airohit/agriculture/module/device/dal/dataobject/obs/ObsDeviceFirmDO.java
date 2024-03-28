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
@TableName("obs_device_firm")
@KeySequence("obs_device_firm_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObsDeviceFirmDO extends BaseDO {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    private Integer firmId;

    /**
     * 农场id
     */
    private Integer farmId;

    /**
     * 用户名
     */
    private String loginName;

    /**
     * 密码
     */
    private String loginPwd;

}
