package com.taskhub.api.module.role.converter;

import com.taskhub.api.module.role.bo.AddRoleBo;
import com.taskhub.api.module.role.dto.AddRoleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleConverter {

    AddRoleBo addRoleDtoToBo(AddRoleDto dto);
}
