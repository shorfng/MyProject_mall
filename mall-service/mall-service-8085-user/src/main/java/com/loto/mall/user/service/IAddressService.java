package com.loto.mall.user.service;

import com.loto.mall.api.user.model.Address;

import java.util.List;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-21 12:39<p>
 * PageName：IAddressService.java<p>
 * Function：
 */

public interface IAddressService {
    /**
     * 查询用户收件地址列表
     * @param userName
     * @return
     */
    List<Address> list(String userName);
}
