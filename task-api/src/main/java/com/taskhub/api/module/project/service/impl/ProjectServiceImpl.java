package com.taskhub.api.module.project.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taskhub.api.common.utils.PageHeaderUtils;
import com.taskhub.api.module.project.bo.AddProjectBo;
import com.taskhub.api.module.project.bo.ProjectQueryBo;
import com.taskhub.api.module.project.converter.ProjectConverter;
import com.taskhub.api.module.project.mapper.ProjectMapperExt;
import com.taskhub.api.module.project.qo.ProjectQueryQo;
import com.taskhub.api.module.project.service.ProjectService;
import com.taskhub.api.module.project.vo.ProjectQueryVo;
import com.taskhub.auto.entity.ProjectDo;
import com.taskhub.auto.mapper.ProjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, ProjectDo> implements ProjectService {

    private final ProjectConverter projectConverter;

    private final ProjectMapperExt projectMapperExt;

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

    @Override
    public Page<ProjectQueryVo> listProjects(ProjectQueryBo projectQueryBo){
        ProjectQueryQo projectQueryQo = projectConverter.projectQueryBoToQo(projectQueryBo);
        Page<ProjectQueryVo> page = projectMapperExt.listProject(new Page<>(PageHeaderUtils.getPageNum(),PageHeaderUtils.getPageSize()),projectQueryQo);
        return page;
    }
}
