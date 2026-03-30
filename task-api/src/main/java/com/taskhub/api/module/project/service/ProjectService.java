package com.taskhub.api.module.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taskhub.api.module.project.bo.AddProjectBo;
import com.taskhub.auto.entity.ProjectDo;

public interface ProjectService extends IService<ProjectDo> {

    int addProject(AddProjectBo addProjectBo);
}

