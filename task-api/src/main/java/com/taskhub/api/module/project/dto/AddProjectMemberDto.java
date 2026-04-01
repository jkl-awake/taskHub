package com.taskhub.api.module.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddProjectMemberDto implements Serializable{

    @Schema(description = "项目ID")
    private Long projectId;
    @Schema(description = "用户ID")
    private Long userId;
    @Schema(description = "角色")
    private String Role;
}
