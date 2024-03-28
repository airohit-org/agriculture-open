package com.airohit.agriculture.module.device.dal.dataobject.obs;

import com.airohit.agriculture.module.device.vo.obs.ObsDeviceNode;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description: 山东仁科设备详细信息
 * @Author: hanliyao
 * @Date: 2023/7/11 17:08
 */
@TableName(value = "obs_device_info", autoResultMap = true)
@KeySequence("obs_device_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObsDeviceInfoDO {

    /**
     * id
     */
/*    @TableId(type = IdType.AUTO)
    private Integer id;*/
    /**
     * 设备id
     */
    private Integer deviceId;
    /**
     * 设备各节点详细数据
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private List<ObsDeviceNode> data;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 最后更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 数据类型
     */
    private String type;
}
