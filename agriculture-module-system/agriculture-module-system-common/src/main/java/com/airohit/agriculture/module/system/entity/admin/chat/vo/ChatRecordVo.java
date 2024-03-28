package com.airohit.agriculture.module.system.entity.admin.chat.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/17 09:55
 */
@Data
@ApiModel("记录对话")
public class ChatRecordVo {
    @ApiModelProperty("对话记录")
    private String record;
    @ApiModelProperty("对话归属记录,bot代表机器人,user代表用户问的")
    private String user;
    @ApiModelProperty("对话归属的主题")
    private String topic;
    @ApiModelProperty("平台,对外部署的直接填00,平台自有的直接填对应的租户ID,app端直接填用户的username")
    private String platform;
    @ApiModelProperty(value = "平台,对外部署的直接填00,平台自有的直接填对应的租户ID", hidden = true)
    private Date createTime = new Date();

    @ApiModelProperty("主题前缀,第一次创建不用传,之后则需要传")
    private String prefix;
}
