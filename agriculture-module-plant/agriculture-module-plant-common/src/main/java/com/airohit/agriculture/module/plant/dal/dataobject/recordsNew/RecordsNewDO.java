package com.airohit.agriculture.module.plant.dal.dataobject.recordsNew;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.airohit.agriculture.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 农事记录 DO
 *
 * @author 管理员
 */
@TableName("agro_records_new")
@KeySequence("agro_records_new_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("")
public class RecordsNewDO extends BaseDO {

    private static final long serialVersionUID = 3103600014591667046L;
    /**
     * id
     */
    @TableId
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * 视频链接
     */
    @ApiModelProperty("视频链接")
    private String videoUrl;
    /**
     * 图片链接
     */
    @ApiModelProperty("图片链接")
    private String imageUrls;
    /**
     * 农事类型
     */
    @ApiModelProperty("农事类型")
    private Integer type;
    /**
     * 任务对应的表名
     */
    @ApiModelProperty(hidden = true)
    private String typeTableName;
    /**
     * 任务对应的模型名称
     */
    @ApiModelProperty(hidden = true)
    private String typeModelName;
    /**
     * 操作时间
     */
    @ApiModelProperty("操作时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date operateTime;
    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private Integer status;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 地块ID
     */
    @ApiModelProperty("地块ID")
    private Integer landId;
    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;
    /**
     * 记录
     */
    @ApiModelProperty("记录")
    private String records;
    /**
     * 种植工序ID
     */
    @ApiModelProperty("种植工序ID")
    private Integer plantProcedureId;

}
