package com.taskhub.api.converter.project;

import com.taskhub.api.model.bo.AddProjectBo;
import com.taskhub.api.model.dto.AddProjectDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectConverter {

    AddProjectBo AddProjectDtoToBo(AddProjectDto addProjectDto);
}

