package com.taskhub.api.module.role.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class AddRoleBo implements Serializable {
    @Schema(description = "角色名称")
    private String roleName;
    @Schema(description = "角色编码",type = "string",required = false)
    private String remark;
}
