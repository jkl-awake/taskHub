package com.taskhub.api.module.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taskhub.api.module.project.service.ProjectMemberService;
import com.taskhub.auto.entity.ProjectMemberDo;
import com.taskhub.auto.mapper.ProjectMemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImpl extends ServiceImpl<ProjectMemberMapper, ProjectMemberDo> implements ProjectMemberService {
}
