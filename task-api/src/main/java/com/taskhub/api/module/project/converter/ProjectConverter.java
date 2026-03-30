package com.taskhub.api.module.project.converter;

import com.taskhub.api.module.project.bo.AddProjectBo;
import com.taskhub.api.module.project.dto.AddProjectDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectConverter {

    AddProjectBo AddProjectDtoToBo(AddProjectDto addProjectDto);


}

