package com.taskhub.api.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserRoleVo implements Serializable {

    private Long roleId;
    private String roleCode;
    private String roleName;
}

