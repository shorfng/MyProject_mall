package com.loto.mall.cart.mapper;

import com.loto.mall.api.cart.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-06-21 9:47<p>
 * PageName：CartMapper.java<p>
 * Function：
 */

public interface CartMapper extends MongoRepository<Cart, String> {

}
