package com.taskhub.api.module.project.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddProjectBo {

    private Long userId;
    private String name;
    private String description;
}

