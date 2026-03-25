package com.example.taskhub.converter.project;

import com.example.taskhub.model.bo.AddProjectBo;
import com.example.taskhub.model.dto.AddProjectDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectConverter {

    AddProjectBo AddProjectDtoToBo(AddProjectDto addProjectDto);
}
