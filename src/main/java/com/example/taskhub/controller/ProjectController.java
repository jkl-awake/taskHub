package com.example.taskhub.controller;

import com.example.taskhub.common.response.ApiResponse;
import com.example.taskhub.converter.project.ProjectConverter;
import com.example.taskhub.model.bo.AddProjectBo;
import com.example.taskhub.model.dto.AddProjectDto;
import com.example.taskhub.service.project.ProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project/")
@RequiredArgsConstructor
@Tag(name = "项目接口", description = "项目相关接口")
public class ProjectController {

    private final ProjectConverter projectConverter;

    private final ProjectService projectService;

    @PostMapping("/addProject")
    public ApiResponse<Integer> addProject(@Valid @RequestBody AddProjectDto addProjectDto){
        AddProjectBo addProjectBo = projectConverter.AddProjectDtoToBo(addProjectDto);
        var rows = projectService.addProject(addProjectBo);
        return rows > 0 ? ApiResponse.success(rows) : ApiResponse.fail("添加项目失败");
    }
}
