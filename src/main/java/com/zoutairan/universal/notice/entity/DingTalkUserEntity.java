package com.zoutairan.universal.notice.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ding_talk_user
 *
 * @author
 */
@Data
@TableName("ding_talk_user")
public class DingTalkUserEntity implements Serializable {
    private Long id;

    /**
     * 用户姓名
     */
    private String name;

    private String cellphoneNumber;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 最近更新人
     */
    private String lastUpdateBy;

    /**
     * 最近更新时间
     */
    private Date lastUpdateTime;

    private static final long serialVersionUID = 1L;
}