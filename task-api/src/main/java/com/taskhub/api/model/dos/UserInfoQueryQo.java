package com.taskhub.api.model.dos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfoQueryQo {
    private Long userId;
}
