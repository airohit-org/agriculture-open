package com.airohit.agriculture.module.content.dal.dataobject.news;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * 新闻信息 DO
 *
 * @author 管理员
 */
@TableName("new_info")
@KeySequence("new_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsDO extends BaseDO {

    /**
     * 编码
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 新闻标题
     */
    private String title;
    /**
     * 图片
     */
    private String imageUrl;
    /**
     * 阅读次数
     */
    private Integer readNum;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 新闻内容
     */
    private String content;
    /**
     * 是否置顶 0 否 1 是
     */
    private Integer isTop;
    /**
     * 发布时间
     */
    private Date publishTime;
    /**
     * 发布状态 1 未发布 2已发布 3已下架
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;

}
