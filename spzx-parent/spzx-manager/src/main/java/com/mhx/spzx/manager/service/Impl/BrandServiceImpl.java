package com.mhx.spzx.manager.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mhx.spzx.manager.mapper.BrandMapper;
import com.mhx.spzx.manager.service.BrandService;
import com.mhx.spzx.model.entity.product.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper ;

    @Override
    public PageInfo<Brand> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        List<Brand> list=brandMapper.findByPage();
        return new PageInfo<>(list);
    }

    @Override
    public void save(Brand brand) {
        brandMapper.save(brand);
    }
    @Override
    public void updateById(Brand brand) {
        brandMapper.updateById(brand) ;
    }
    @Override
    public void deleteById(Long id) {
        brandMapper.deleteById(id) ;
    }

    @Override
    public List<Brand> findAll() {
        return brandMapper.findByPage();
    }
}