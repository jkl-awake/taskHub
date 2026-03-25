package com.example.taskhub.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddProjectDto {

    @Schema(description = "负责人id", example = "1")
    private Long userId;

    @Schema(description = "项目名称", example = "任务管理系统")
    private String name;

    @Schema(description = "项目描述", example = "这是一个用于管理任务的系统")
    private String description;
}
