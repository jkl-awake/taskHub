package com.taskhub.api.service.user.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taskhub.api.converter.user.UserConverter;
import com.taskhub.api.model.bo.UserInfoQueryBo;
import com.taskhub.api.model.dos.UserInfoQueryQo;
import com.taskhub.api.model.vo.UserInfoQueryVo;
import com.taskhub.api.service.user.UserService;
import com.taskhub.auto.entity.SysUserDo;
import com.taskhub.auto.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUserDo> implements UserService {
    private final UserConverter userConverter;


    public UserInfoQueryVo me(UserInfoQueryBo userInfoQueryBo){
        SysUserDo userDo = this.getById(userInfoQueryBo.getUserId());
        if(ObjectUtil.isEmpty(userDo)){
            return null;
        }
        return userConverter.userInfoQueryDoToVo(userDo);
    }

}

