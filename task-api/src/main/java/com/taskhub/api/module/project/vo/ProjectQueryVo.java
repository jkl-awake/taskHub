package com.taskhub.api.module.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectQueryVo {

    @Schema(description = "项目id")
    private Long id;
    @Schema(description = "项目名称")
    private String projectName;
    @Schema(description = "项目描述")
    private String desc;
    @Schema(description = "项目负责人id")
    private long ownerId;
    @Schema(description = "项目负责人名称")
    private String ownerName;
    @Schema(description = "创建时间")
    private String createdAt;
    @Schema(description = "更新时间")
    private String updatedAt;
}
