package com.example.taskhub.model.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ticket")
public class TicketPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("ticket_no")
    private String ticketNo;

    @TableField("title")
    private String title;

    @TableField("description")
    private String description;

    @TableField("project_id")
    private Long projectId;

    @TableField("reporter_id")
    private Long reporterId;

    @TableField("assignee_id")
    private Long assigneeId;

    @TableField("status")
    private String status;

    @TableField("priority")
    private String priority;

    @TableField("deadline")
    private LocalDateTime deadline;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;

    @TableField("created_by")
    private Long createdBy;

    @TableField("updated_by")
    private Long updatedBy;

    @TableLogic
    @TableField("deleted")
    private Integer deleted;
}
