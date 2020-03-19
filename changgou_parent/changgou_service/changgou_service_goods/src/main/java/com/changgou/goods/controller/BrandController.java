package com.changgou.goods.controller;

import com.github.pagehelper.Page;
import com.changgou.entity.PageResult;
import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    private BrandService    brandService;

    @GetMapping
    public Result<List<Brand>> findList()
    {
        List<Brand> brandList= brandService.findList();
        return new Result<List<Brand>>(true, StatusCode.OK,"查询成功",brandList);
    }

    @GetMapping("/{id}")
    public Result<Brand> findId(@PathVariable("id") Integer id)
    {
        Brand brand= brandService.findId(id);
        return new Result<Brand>(true, StatusCode.OK,"查询成功",brand);
    }

    @PostMapping
    public Result add(@RequestBody Brand brand)
    {
         brandService.add(brand);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable("id") Integer id, @RequestBody Brand brand)
    {
        brand.setId(id);
        brandService.update(brand);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    @DeleteMapping("/{id}")
    public Result delById(@PathVariable("id") Integer id)
    {
        brandService.delById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }
    @PostMapping("/search")
    public  Result<List<Brand>> search(@RequestParam Map<String, Object> searchMap)
    {
        List<Brand> list= brandService.list(searchMap);
        return new Result<List<Brand>>(true,StatusCode.OK,"查询成功",list);
    }

    @PostMapping("/search/{page}/{size}")
    public  Result<List<Brand>> findPage(@PathVariable("page") int page,@PathVariable("size") int size)
    {
        Page<Brand> pageInfo = brandService.findPage(page,size);
        PageResult  pageResult=new PageResult(pageInfo.getTotal(),pageInfo.getResult());
        return new Result<List<Brand>>(true,StatusCode.OK,"查询成功",pageResult);
    }

    @PostMapping("/searchPage/{page}/{size}")
    public  Result<List<Brand>> findPage(@RequestParam Map searchMap, @PathVariable("page") int page,@PathVariable("size") int size)
    {
        int i=1/0;
        Page<Brand> pageInfo = brandService.findPage(searchMap,page,size);
        PageResult  pageResult=new PageResult(pageInfo.getTotal(),pageInfo.getResult());
        return new Result<List<Brand>>(true,StatusCode.OK,"查询成功",pageResult);
    }
}
