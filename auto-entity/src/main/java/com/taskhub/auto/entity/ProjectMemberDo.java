package com.taskhub.auto.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 项目成员表
 *
 * @author Codex
 * @since 2026-03-31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("project_member")
public class ProjectMemberDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 项目ID
     */
    @TableField("project_id")
    private Long projectId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 成员角色: OWNER/MANAGER/MEMBER
     */
    @TableField("member_role")
    private String memberRole;

    /**
     * 加入时间
     */
    @TableField("joined_at")
    private LocalDateTime joinedAt;
}
