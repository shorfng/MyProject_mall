package com.loto.mall.service.goods.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.loto.mall.api.goods.model.Brand;
import com.loto.mall.service.goods.service.IBrandService;
import com.loto.mall.util.common.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/brand")
@Api(value = "BrandController", tags = "品牌管理")
public class BrandController {
    @Autowired
    private IBrandService brandService;

    @ApiOperation(value = "增加品牌信息")
    @PostMapping
    public RespResult add(@RequestBody Brand brand) {
        brandService.save(brand);
        return RespResult.ok();
    }

    @ApiOperation(value = "修改品牌信息")
    @PutMapping
    public RespResult update(@RequestBody Brand brand) {
        brandService.updateById(brand);
        return RespResult.ok();
    }

    @ApiOperation(value = "删除品牌信息（根据品牌id）")
    @DeleteMapping("/{id}")
    public RespResult delete(@PathVariable(value = "id") String id) {
        brandService.removeById(id);
        return RespResult.ok();
    }

    @ApiOperation(value = "查询品牌信息（根据条件查询）")
    @PostMapping(value = "/search")
    public RespResult<List<Brand>> queryList(@RequestBody Brand brand) {
        List<Brand> brands = brandService.queryList(brand);
        return RespResult.ok(brands);
    }

    @ApiOperation(value = "查询品牌信息（分页查询）")
    @PostMapping(value = "/search/{page}/{size}")
    public RespResult<Page<Brand>> queryPageList(
            @PathVariable(value = "page") Long page,
            @PathVariable(value = "size") Long size,
            @RequestBody Brand brand) {

        Page<Brand> pageInfo = brandService.queryPageList(brand, page, size);
        return RespResult.ok(pageInfo);
    }

    @ApiOperation(value = "查询品牌信息（根据分类ID）")
    @GetMapping(value = "/category/{parentId}")
    public RespResult<List<Brand>> categoryBrands(@PathVariable(value = "parentId") Integer parentId) {
        List<Brand> brands = brandService.queryByCategoryId(parentId);
        return RespResult.ok(brands);
    }
}
