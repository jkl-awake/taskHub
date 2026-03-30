package com.taskhub.api.module.project.vo;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectQueryVo {

    private Long id;
    private String projectName;
    private String desc;
}
