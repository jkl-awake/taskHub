package com.taskhub.api.module.user.qo;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfoQueryQo {
    private Long userId;
}
