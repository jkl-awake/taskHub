package com.taskhub.api.module.user.vo;

import com.taskhub.api.module.role.vo.GetUserRoleVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoQueryVo implements Serializable {

    private Long id;
    private String userName;
    private String nickName;
    private String email;
    private String phone;
    private String avatarUrl;
    private List<GetUserRoleVo> roles;
}

