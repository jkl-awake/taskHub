package com.taskhub.api.module.project.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taskhub.api.module.project.bo.AddProjectBo;
import com.taskhub.api.module.project.bo.AddProjectMemberBo;
import com.taskhub.api.module.project.bo.ProjectQueryBo;
import com.taskhub.api.module.project.vo.ProjectQueryVo;
import com.taskhub.auto.entity.ProjectDo;

public interface ProjectService extends IService<ProjectDo> {

    int addProject(AddProjectBo addProjectBo);

    Page<ProjectQueryVo> listProjects(ProjectQueryBo projectQueryBo);

    Integer addProjectMember(AddProjectMemberBo addProjectMemberBo);
}

