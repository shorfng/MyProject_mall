package com.loto.mall.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loto.mall.api.user.model.Address;
import com.loto.mall.user.mapper.AddressMapper;
import com.loto.mall.user.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-21 12:39<p>
 * PageName：AddressServiceImpl.java<p>
 * Function：
 */

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {
    @Autowired
    private AddressMapper addressMapper;

    /**
     * 查询用户收件地址列表
     * @param userName
     * @return
     */
    @Override
    public List<Address> list(String userName) {
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        return addressMapper.selectList(queryWrapper);
    }
}
