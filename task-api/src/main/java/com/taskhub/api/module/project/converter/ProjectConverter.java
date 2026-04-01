package com.taskhub.api.module.project.converter;

import com.taskhub.api.module.project.bo.AddProjectBo;
import com.taskhub.api.module.project.bo.AddProjectMemberBo;
import com.taskhub.api.module.project.bo.ProjectQueryBo;
import com.taskhub.api.module.project.dto.AddProjectDto;
import com.taskhub.api.module.project.dto.AddProjectMemberDto;
import com.taskhub.api.module.project.dto.ProjectQueryDto;
import com.taskhub.api.module.project.qo.ProjectQueryQo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectConverter {

    AddProjectBo AddProjectDtoToBo(AddProjectDto addProjectDto);

    ProjectQueryBo projectQueryDtoToBo(ProjectQueryDto projectQueryDto);
    ProjectQueryQo projectQueryBoToQo(ProjectQueryBo projectQueryBo);

    AddProjectMemberBo addProjectMemberDtoToBo(AddProjectMemberDto addProjectMemberDto);
}

