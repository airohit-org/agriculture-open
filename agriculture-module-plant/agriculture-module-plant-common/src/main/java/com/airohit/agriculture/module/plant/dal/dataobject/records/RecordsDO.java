package com.airohit.agriculture.module.plant.dal.dataobject.records;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * 农事记录 DO
 *
 * @author 管理员
 */
@TableName("agro_records")
@KeySequence("agro_records_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordsDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Integer id;
    /**
     * 视频链接
     */
    private String videoUrl;
    /**
     * 图片链接
     */
    private String imageUrls;
    /**
     * 农事类型
     */
    private Integer type;
    /**
     * 任务对应的表名
     */
    private String typeTableName;
    /**
     * 任务对应的模型名称
     */
    private String typeModelName;
    /**
     * 操作时间
     */
    private Date operateTime;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;

    /**
     * 地块ID
     */
    private Integer landId;
    /**
     * 名称
     */
    private String name;
    /**
     * 记录
     */
    private String records;

}
