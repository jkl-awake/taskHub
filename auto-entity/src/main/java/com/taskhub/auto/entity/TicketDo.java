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
 * 工单表
 *
 * @author Codex
 * @since 2026-03-31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ticket")
public class TicketDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 工单编号
     */
    @TableField("ticket_no")
    private String ticketNo;

    /**
     * 工单标题
     */
    @TableField("title")
    private String title;

    /**
     * 工单描述
     */
    @TableField("description")
    private String description;

    /**
     * 所属项目ID
     */
    @TableField("project_id")
    private Long projectId;

    /**
     * 提交人ID
     */
    @TableField("reporter_id")
    private Long reporterId;

    /**
     * 处理人ID
     */
    @TableField("assignee_id")
    private Long assigneeId;

    /**
     * 状态: TODO/IN_PROGRESS/DONE/CLOSED
     */
    @TableField("status")
    private String status;

    /**
     * 优先级: LOW/MEDIUM/HIGH/URGENT
     */
    @TableField("priority")
    private String priority;

    /**
     * 截止时间
     */
    @TableField("deadline")
    private LocalDateTime deadline;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;

    /**
     * 创建人
     */
    @TableField("created_by")
    private Long createdBy;

    /**
     * 更新人
     */
    @TableField("updated_by")
    private Long updatedBy;

    /**
     * 逻辑删除
     */
    @TableField("deleted")
    private int deleted;
}
