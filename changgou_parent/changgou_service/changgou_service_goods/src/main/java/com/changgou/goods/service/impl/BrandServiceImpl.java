package com.changgou.goods.service.impl;

import com.github.pagehelper.Page;
import com.changgou.goods.dao.BrandMapper;
import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandMapper     brandMapper;

    /**
     * 品牌列表查询
     * @return
     */
    @Override
    public List<Brand> findList() {
        List<Brand> brandList= brandMapper.selectAll();
        return  brandList;
    }

    /**
     * 根据id查询品牌信息
     * @param id
     * @return
     */
    @Override
    public Brand findId(Integer id) {
        Brand brand=brandMapper.selectByPrimaryKey(id);
        return brand;
    }

    @Override
    @Transactional
    public void add(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    @Override
    @Transactional
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKey(brand);
    }

    @Override
    public void delById(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Brand> list(Map<String, Object> searchMap) {
        Example example=new Example(Brand.class);
        Example.Criteria criteria=example.createCriteria();
        if(searchMap!=null)
        {
            if(searchMap.get("name")!=null&&!"".equals(searchMap.get("name"))) {
                criteria.andLike("name","%"+searchMap.get("name")+"%");
            }
            if(searchMap.get("letter")!=null&&!"".equals(searchMap.get("letter"))) {
                criteria.andLike("letter","%"+searchMap.get("letter")+"%");
            }
        }
        List<Brand> brandList= brandMapper.selectByExample(example);
        return brandList;
    }

    @Override
    public Page<Brand> findPage(int page, int size) {
        PageHelper.startPage(page,size);
        Page<Brand> page1= (Page<Brand>) brandMapper.selectAll();
        return page1;
    }

    @Override
    public Page<Brand> findPage(Map searchMap, int page, int size) {
        PageHelper.startPage(page,size);
        Example example=new Example(Brand.class);
        Example.Criteria criteria=example.createCriteria();
        if(searchMap!=null)
        {
            if(searchMap.get("name")!=null&&!"".equals(searchMap.get("name")))
            {
                criteria.andLike("name","%"+searchMap.get("name")+"%");
            }
            if(searchMap.get("letter")!=null&&!"".equals(searchMap.get("letter"))) {
                criteria.andLike("letter","%"+searchMap.get("letter")+"%");
            }
        }
        Page<Brand> page1=(Page<Brand>) brandMapper.selectByExample(example);
        return page1;
    }

}
