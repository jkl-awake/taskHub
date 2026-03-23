package com.example.taskhub.model.po;

import com.baomidou.mybatisplus.annotation.*;
import com.example.taskhub.common.constant.SecurityConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role")
public class SysRolePo implements Serializable {

    public static SysRolePo defaultRole() {
        LocalDateTime now = LocalDateTime.now();
        return SysRolePo.builder()
                .roleCode(SecurityConstants.DEFAULT_ROLE_CODE)
                .roleName("管理员")
                .remark("系统默认角色")
                .createdAt(now)
                .updatedAt(now)
                .deleted(false)
                .build();
    }

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("role_code")
    private String roleCode;

    @TableField("role_name")
    private String roleName;

    @TableField("remark")
    private String remark;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;

    @TableLogic
    @TableField("deleted")
    private boolean deleted;
}
