package com.taskhub.api.security.model;

import com.taskhub.auto.entity.SysUserDo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserBo implements Serializable {

    public static AuthUserBo defaultAuthUserBo(SysUserDo sysUserPo, String sessionId, List<String> roleCodes, List<String> permissions) {
        return AuthUserBo.builder()
                .userId(sysUserPo.getId())
                .username(sysUserPo.getUserName())
                .nickname(sysUserPo.getNickName())
                .email(sysUserPo.getEmail())
                .status(sysUserPo.getStatus())
                .sessionId(sessionId)
                .roleCodes(roleCodes)
                .permissions(permissions)
                .build();
    }

    private Long userId;
    private String username;
    private String nickname;
    private String email;
    private Integer status;
    private String sessionId;
    private List<String> roleCodes;
    private List<String> permissions;
}
