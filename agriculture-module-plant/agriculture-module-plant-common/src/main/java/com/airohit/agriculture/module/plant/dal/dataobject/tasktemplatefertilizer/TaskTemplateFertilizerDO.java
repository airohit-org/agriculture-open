package com.airohit.agriculture.module.plant.dal.dataobject.tasktemplatefertilizer;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 施肥 DO
 *
 * @author 史铭浩
 */
@TableName("agro_task_template_fertilizer")
@KeySequence("agro_task_template_fertilizer_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskTemplateFertilizerDO extends BaseDO {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 任务基本信息id
     */
    private Integer agroTaskTemplateId;
    /**
     * 施肥类型
     */
    private String fertilizationType;
    /**
     * 基肥名称
     */
    private String baseFertilizerName;
    /**
     * 基肥用量
     */
    private String baseFertilizerDosage;
    /**
     * 农机
     */
    private String farmMachinery;
    /**
     * 备注
     */
    private String remark;

}
