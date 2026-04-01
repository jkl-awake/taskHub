package com.taskhub.api.module.project.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taskhub.api.common.utils.PageHeaderUtils;
import com.taskhub.api.module.project.bo.AddProjectBo;
import com.taskhub.api.module.project.bo.AddProjectMemberBo;
import com.taskhub.api.module.project.bo.ProjectQueryBo;
import com.taskhub.api.module.project.converter.ProjectConverter;
import com.taskhub.api.module.project.mapper.ProjectMapperExt;
import com.taskhub.api.module.project.qo.ProjectQueryQo;
import com.taskhub.api.module.project.service.ProjectMemberService;
import com.taskhub.api.module.project.service.ProjectService;
import com.taskhub.api.module.project.vo.ProjectQueryVo;
import com.taskhub.api.module.user.service.UserService;
import com.taskhub.auto.entity.ProjectDo;
import com.taskhub.auto.entity.ProjectMemberDo;
import com.taskhub.auto.entity.SysUserDo;
import com.taskhub.auto.entity.SysUserRoleDo;
import com.taskhub.auto.mapper.ProjectMapper;
import com.taskhub.auto.mapper.ProjectMemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, ProjectDo> implements ProjectService {

    private final ProjectConverter projectConverter;
    private final ProjectMapperExt projectMapperExt;
    private final ProjectMemberService projectMemberService;
    private final UserService userService;

    @Override
    public int addProject(AddProjectBo addProjectBo) {
        LocalDateTime now = LocalDateTime.now();
        ProjectDo projectDo = ProjectDo.builder()
                .projectCode(UUID.randomUUID().toString())
                .projectName(addProjectBo.getName())
                .description(addProjectBo.getDescription())
                .ownerId(addProjectBo.getUserId())
                .status(1)
                .createdAt(now)
                .updatedAt(now)
                .deleted(0)
                .build();
        save(projectDo);
        return 1;
    }

    @Override
    public Page<ProjectQueryVo> listProjects(ProjectQueryBo projectQueryBo){
        ProjectQueryQo projectQueryQo = projectConverter.projectQueryBoToQo(projectQueryBo);
        var pageNum = PageHeaderUtils.getPageNum();
        var pageSize = PageHeaderUtils.getPageSize();
        Page<ProjectQueryVo> page = projectMapperExt.listProject(new Page<>(pageNum,pageSize),projectQueryQo);
        if(!page.getRecords().isEmpty()){
            List<Long> memberIds = page.getRecords().stream()
                    .map(ProjectQueryVo::getMembers)
                    .filter(StringUtils::isNotBlank)
                    .flatMap(members -> Arrays.stream(members.split(",")))
                    .map(String::trim)
                    .filter(StringUtils::isNotBlank)
                    .distinct()
                    .map(Long::valueOf)
                    .toList();
            List<SysUserDo> memberInfos = userService.list(
                    Wrappers.<SysUserDo>lambdaQuery()
                            .select(SysUserDo::getId,SysUserDo::getUserName)
                            .in(SysUserDo::getId, memberIds)
            );
            page.getRecords().forEach(projectQueryVo -> {
                if(StringUtils.isNotBlank(projectQueryVo.getMembers())){
                    projectQueryVo.setMembers(packListProjectMember(memberInfos,projectQueryVo.getMembers()));
                }
            });
        }

        return page;
    }

    public Integer addProjectMember(AddProjectMemberBo addProjectMemberBo){
        ProjectMemberDo projectMemberDo = ProjectMemberDo.builder()
                .userId(addProjectMemberBo.getUserId())
                .projectId(addProjectMemberBo.getProjectId())
                .memberRole(addProjectMemberBo.getRole())
                .joinedAt(LocalDateTime.now())
                .build();
        projectMemberService.save(projectMemberDo);
        return 1;
    }

    private String packListProjectMember(List<SysUserDo> memberInfos,String memberIds){
        List<Long> memberIdList = Arrays.stream(memberIds.split(","))
                .map(String::trim)
                .filter(StringUtils::isNotBlank)
                .map(Long::valueOf)
                .toList();
        List<String> memberNames = memberInfos.stream()
                .filter(user -> memberIdList.contains(user.getId()))
                .map(SysUserDo::getUserName)
                .toList();
        return String.join(",", memberNames);
    }
}
