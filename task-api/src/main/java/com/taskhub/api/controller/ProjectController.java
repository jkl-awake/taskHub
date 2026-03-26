package com.taskhub.api.controller;

import com.taskhub.api.common.response.ApiResponse;
import com.taskhub.api.converter.project.ProjectConverter;
import com.taskhub.api.model.bo.AddProjectBo;
import com.taskhub.api.model.dto.AddProjectDto;
import com.taskhub.api.service.project.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
@Tag(name = "项目接口", description = "提供项目创建等基础能力")
public class ProjectController {

    private final ProjectConverter projectConverter;
    private final ProjectService projectService;

    @PostMapping("/addProject")
    @Operation(summary = "新增项目")
    public ApiResponse<Integer> addProject(@Valid @RequestBody AddProjectDto addProjectDto) {
        AddProjectBo addProjectBo = projectConverter.AddProjectDtoToBo(addProjectDto);
        int rows = projectService.addProject(addProjectBo);
        return rows > 0 ? ApiResponse.success(rows) : ApiResponse.fail("新增项目失败");
    }
}
