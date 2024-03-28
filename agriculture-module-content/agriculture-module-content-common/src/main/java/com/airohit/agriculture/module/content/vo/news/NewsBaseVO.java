package com.airohit.agriculture.module.content.vo.news;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 新闻信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class NewsBaseVO {

    @ApiModelProperty(value = "新闻标题")
    private String title;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "是否置顶 0 否 1 是", required = true)
    @NotNull(message = "是否置顶 0 否 1 是不能为空")
    private Integer isTop;

    @ApiModelProperty(value = "发布时间")
    private Date publishTime;

    @ApiModelProperty(value = "发布状态 1 未发布 2已发布 3已下架", required = true)
    @NotNull(message = "发布状态 1 未发布 2已发布 3已下架不能为空")
    private Integer status;

    @ApiModelProperty(value = "新闻内容")
    private String content;

    @ApiModelProperty(value = "阅读次数")
    private Integer readNum;
}
