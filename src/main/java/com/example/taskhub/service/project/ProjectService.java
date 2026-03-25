package com.example.taskhub.service.project;

import com.example.taskhub.model.bo.AddProjectBo;
import com.example.taskhub.model.dto.AddProjectDto;

public interface ProjectService {

    int addProject(AddProjectBo addProjectBo);
}
