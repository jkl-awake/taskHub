package com.taskhub.api.module.project.bo;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectQueryBo {
    private String keyword;
}
