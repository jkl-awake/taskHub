package com.taskhub.api.module.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taskhub.api.module.user.bo.UserInfoQueryBo;
import com.taskhub.api.module.user.converter.UserConverter;
import com.taskhub.api.module.user.service.UserService;
import com.taskhub.api.module.user.vo.UserInfoQueryVo;
import com.taskhub.auto.entity.SysUserDo;
import com.taskhub.auto.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUserDo> implements UserService {
    private final UserConverter userConverter;


    /*
    * @author work-ljk
    * 用户中心
    * */
    @Override
    public UserInfoQueryVo me(UserInfoQueryBo userInfoQueryBo){
        SysUserDo userDo = this.getById(userInfoQueryBo.getUserId());
        if(ObjectUtil.isEmpty(userDo)){
            return null;
        }
        return userConverter.userInfoQueryDoToVo(userDo);
    }
}

