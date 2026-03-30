package com.taskhub.api.module.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoQueryDto implements Serializable {

    @NotNull(message = "用户 ID 不能为空")
    @Schema(description = "用户 ID", example = "1")
    private Long userId;
}
