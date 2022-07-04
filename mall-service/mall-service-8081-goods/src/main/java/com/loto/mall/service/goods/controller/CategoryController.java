package com.loto.mall.service.goods.controller;

import com.loto.mall.api.goods.model.Category;
import com.loto.mall.service.goods.service.ICategoryService;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 商品类目 前端控制器
 * </p>
 *
 * @author 蓝田_Loto
 * @since 2022-06-13 22:42:39
 */
@RestController
@RequestMapping("/category")
@Api(value = "CategoryController", tags = "商品类目")
@CrossOrigin
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @ApiOperation(value = "查询所有子类（根据分类的 parentId）")
    @GetMapping(value = "/parent/{parentId}")
    public RespResult<List<Category>> findByParentId(@PathVariable("parentId") Integer parentId) {
        return RespResult.ok(categoryService.findByParentId(parentId));
    }

    @ApiOperation(value = "根据分类查询分类信息")
    @GetMapping(value = "/{id}")
    public RespResult<Category> one(@PathVariable(value = "id") Integer id) {
        Category category = categoryService.getById(id);
        return RespResult.ok(category);
    }
}
