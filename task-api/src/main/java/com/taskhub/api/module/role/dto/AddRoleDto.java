package com.taskhub.api.module.role.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class AddRoleDto implements Serializable {

    @Schema(description = "角色名称",type = "string",required = true)
    private String roleName;
    @Schema(description = "角色编码",type = "string",required = false)
    private String remark;
}
