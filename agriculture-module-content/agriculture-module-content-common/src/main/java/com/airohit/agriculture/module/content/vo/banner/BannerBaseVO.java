package com.airohit.agriculture.module.content.vo.banner;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 广告信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class BannerBaseVO {

    @ApiModelProperty(value = "广告链接")
    private String link;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "广告图片地址")
    private String url;

    @ApiModelProperty(value = "截至日期")
    private Date endTime;

    @ApiModelProperty(value = "app广告图片地址")
    private String appUrl;

    @ApiModelProperty(value = "状态（0正常 1关闭）")
    private Integer status;

}
