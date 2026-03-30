package com.taskhub.api.module.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taskhub.api.module.project.bo.AddProjectBo;
import com.taskhub.api.module.project.service.ProjectService;
import com.taskhub.auto.entity.ProjectDo;
import com.taskhub.auto.mapper.ProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, ProjectDo> implements ProjectService {

    @Override
    public int addProject(AddProjectBo addProjectBo) {
        LocalDateTime now = LocalDateTime.now();
        ProjectDo projectDo = ProjectDo.builder()
                .projectCode(UUID.randomUUID().toString())
                .projectName(addProjectBo.getName())
                .description(addProjectBo.getDescription())
                .ownerId(addProjectBo.getUserId())
                .status(1)
                .createdAt(now)
                .updatedAt(now)
                .deleted(0)
                .build();
        save(projectDo);
        return 1;
    }


}
