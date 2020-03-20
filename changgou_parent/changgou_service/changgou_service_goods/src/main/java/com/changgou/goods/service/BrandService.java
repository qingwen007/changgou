package com.changgou.goods.service;

import com.github.pagehelper.Page;
import com.changgou.goods.pojo.Brand;

import java.util.List;
import java.util.Map;

public interface BrandService {
    /***
     * 查询所有品牌
     * @return
     */
    public List<Brand> findList();

    /**
     * 根据id查询品牌数据
     */
    Brand findId(Integer id);

    void add(Brand brand);

    void update(Brand brand);

    void delById(Integer id);

    List<Brand> list(Map<String,Object> searchMap);

    Page<Brand> findPage(int page, int size);

    Page<Brand> findPage(Map searchMap, int page, int size);
}
