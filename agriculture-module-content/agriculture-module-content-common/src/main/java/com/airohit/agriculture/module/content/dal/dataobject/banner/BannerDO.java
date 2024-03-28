package com.airohit.agriculture.module.content.dal.dataobject.banner;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * 广告信息 DO
 *
 * @author 管理员
 */
@TableName("banner")
@KeySequence("banner_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerDO extends BaseDO {

    /**
     * 编码
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 连接
     */
    private String link;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 地址
     */
    private String url;
    /**
     * 截至日期
     */
    private Date endTime;
    /**
     * app地址
     */
    private String appUrl;
    /**
     * 状态（0正常 1关闭）
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;

}
