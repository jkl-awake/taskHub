package com.example.taskhub.service.project.impl;

import com.example.taskhub.model.bo.AddProjectBo;
import com.example.taskhub.model.po.ProjectPo;
import com.example.taskhub.repository.project.ProjectMapperExt;
import com.example.taskhub.service.project.ProjectService;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectMapperExt projectMapperExt;


    /*
    * 新增项目
    * */
    @Override
    public int addProject(AddProjectBo addProjectBo){
        ProjectPo projectPo = ProjectPo.defaultProjectPo(addProjectBo.getUserId(), addProjectBo.getName(), addProjectBo.getDescription());
        return projectMapperExt.insert(projectPo);
    }
}
