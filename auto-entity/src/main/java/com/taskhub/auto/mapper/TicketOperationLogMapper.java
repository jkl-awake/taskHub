package com.taskhub.auto.mapper;

import com.taskhub.auto.entity.TicketOperationLogDo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 工单操作日志表 Mapper 接口
 * </p>
 *
 * @author Codex
 * @since 2026-03-31
 */
@Mapper
public interface TicketOperationLogMapper extends BaseMapper<TicketOperationLogDo> {

}
