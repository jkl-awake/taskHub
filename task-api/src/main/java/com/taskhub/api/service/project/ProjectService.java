package com.taskhub.api.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taskhub.api.model.bo.AddProjectBo;
import com.taskhub.api.model.dto.AddProjectDto;
import com.taskhub.auto.entity.ProjectDo;

public interface ProjectService extends IService<ProjectDo> {

    int addProject(AddProjectBo addProjectBo);
}

