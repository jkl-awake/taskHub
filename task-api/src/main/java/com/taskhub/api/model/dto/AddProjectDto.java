package com.taskhub.api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddProjectDto {

    @Schema(description = "用户ID", example = "1")
    private Long userId;

    @NotBlank(message = "项目名称不能为空")
    @Schema(description = "项目名称", example = "任务管理系统")
    private String name;

    @Schema(description = "项目描述", example = "这是一个任务管理系统的项目描述示例")
    private String description;
}
