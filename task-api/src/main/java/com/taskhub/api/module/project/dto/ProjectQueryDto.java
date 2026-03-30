package com.taskhub.api.module.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectQueryDto implements Serializable {

    @Schema(description = "项目名称")
    private String projectName;
    @Schema(description = "负责人名称")
    private String ownerName;
}
