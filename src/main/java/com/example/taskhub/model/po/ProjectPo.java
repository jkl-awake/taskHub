package com.example.taskhub.model.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("project")
public class ProjectPo implements Serializable {

    public static ProjectPo defaultProjectPo(Long ownerId,String projectName,String description){
        LocalDateTime now = LocalDateTime.now();
        return ProjectPo.builder()
                .projectCode(UUID.randomUUID().toString())
                .projectName(projectName)
                .description(description)
                .ownerId(ownerId)
                .status(1)
                .createdAt(now)
                .updatedAt(now)
                .deleted(false)
                .build();
    }

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("project_code")
    private String projectCode;

    @TableField("project_name")
    private String projectName;

    @TableField("description")
    private String description;

    @TableField("status")
    private int status;

    @TableField("owner_id")
    private Long ownerId;

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
    private boolean deleted;
}
