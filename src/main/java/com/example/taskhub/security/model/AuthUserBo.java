package com.example.taskhub.security.model;

import com.example.taskhub.model.po.SysUserPo;
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

    /*
    * 构建默认的AuthUserBo对象
    * */
    public static AuthUserBo defaultAuthUserBo(SysUserPo sysUserPo,String sessionId,List<String> roleCodes,List<String> permissions){
        return AuthUserBo.builder()
                .userId(sysUserPo.getId())
                .username(sysUserPo.getUsername())
                .nickname(sysUserPo.getNickname())
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
