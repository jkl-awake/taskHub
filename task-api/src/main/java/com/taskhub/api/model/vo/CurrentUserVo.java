package com.taskhub.api.model.vo;

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
public class CurrentUserVo implements Serializable {

    private Long id;
    private String username;
    private String nickname;
    private String email;
    private List<String> roleCodes;
    private List<String> permissions;
}

