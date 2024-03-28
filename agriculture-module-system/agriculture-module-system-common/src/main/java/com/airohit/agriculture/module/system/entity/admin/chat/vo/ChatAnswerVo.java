package com.airohit.agriculture.module.system.entity.admin.chat.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/18 10:09
 */
@Data
@ApiModel("获取答案")
public class ChatAnswerVo {
    @ApiModelProperty("问题")
    private String question;
    @ApiModelProperty("token")
    private String token;
}
