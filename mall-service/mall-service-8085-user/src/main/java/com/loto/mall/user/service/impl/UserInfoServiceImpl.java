package com.loto.mall.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loto.mall.api.user.model.UserInfo;
import com.loto.mall.user.mapper.UserInfoMapper;
import com.loto.mall.user.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-30 19:25<p>
 * PageName：UserInfoServiceImpl.java<p>
 * Function：
 */

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
