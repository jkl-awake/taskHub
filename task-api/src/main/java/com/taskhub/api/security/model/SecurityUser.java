package com.taskhub.api.security.model;

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
public class SecurityUser implements Serializable {

    private Long userId;
    private String username;
    private String nickname;
    private List<String> roleCodes;
    private List<String> permissions;
}

