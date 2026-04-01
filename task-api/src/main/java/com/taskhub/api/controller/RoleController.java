package com.taskhub.api.controller;

import com.taskhub.api.common.response.ApiResponse;
import com.taskhub.api.module.role.bo.AddRoleBo;
import com.taskhub.api.module.role.converter.RoleConverter;
import com.taskhub.api.module.role.dto.AddRoleDto;
import com.taskhub.api.module.role.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
@Tag(name = "角色接口", description = "角色管理")
public class RoleController {

    private final RoleService roleService;
    private final RoleConverter roleConverter;

    @PostMapping("/addRole")
    @Operation(description = "新增角色")
    public ApiResponse<String> addRole(@RequestBody AddRoleDto addRoleDto){
        AddRoleBo addRoleBo =roleConverter.addRoleDtoToBo(addRoleDto);
        String roleCode = roleService.addRole(addRoleBo);
        return roleCode != null ? ApiResponse.success(roleCode) : ApiResponse.fail("新增角色失败");
    }
}
