package com.taskhub.api.module.project.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taskhub.api.module.project.qo.ProjectQueryQo;
import com.taskhub.api.module.project.vo.ProjectQueryVo;
import org.apache.ibatis.annotations.Param;

public interface ProjectMapperExt {

    /**
     * 获取项目列表
     *
     * @param page 分页参数
     * @param projectQueryQo {@link ProjectQueryQo}
     * @return {@link Page<ProjectQueryVo>}
     * */
    Page<ProjectQueryVo> listProject(Page<?> page, @Param("projectQueryQo")ProjectQueryQo projectQueryQo);
}
