package com.mhx.spzx.manager.service.Impl;

import com.alibaba.excel.EasyExcel;
import com.mhx.spzx.common.exception.BaseException;
import com.mhx.spzx.manager.listener.ExcelListener;
import com.mhx.spzx.manager.mapper.CategoryMapper;
import com.mhx.spzx.manager.service.CategoryService;
import com.mhx.spzx.model.entity.product.Category;
import com.mhx.spzx.model.vo.common.Result;
import com.mhx.spzx.model.vo.common.ResultCodeEnum;
import com.mhx.spzx.model.vo.product.CategoryExcelVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    @Transactional
    public List<Category> findCategoryList(Long id) {
        List<Category> categoryList=categoryMapper.selectCategoryByParentId(id);
        if(!CollectionUtils.isEmpty(categoryList)){
            for (Category category : categoryList) {
                int cnt=categoryMapper.selectCountByParentId(category.getId());
                category.setHasChildren(cnt > 0);
            }
        }
        return categoryList;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        try {
            // 设置响应结果类型
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("分类数据", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            List<Category> categoryList =categoryMapper.findAll();
            List<CategoryExcelVo> categoryExcelVoList=new ArrayList<>();
            for (Category category : categoryList) {
                CategoryExcelVo categoryExcelVo=new CategoryExcelVo();
                BeanUtils.copyProperties(category,categoryExcelVo);
                categoryExcelVoList.add(categoryExcelVo);
            }
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class)
                    .sheet("分类数据").doWrite(categoryExcelVoList);
        } catch (Exception e) {
            throw new BaseException(ResultCodeEnum.DATA_ERROR);
        }
    }

    @Override
    public void importData(MultipartFile file) {
        try {
            //创建监听器对象，传递mapper对象
            ExcelListener<CategoryExcelVo> excelListener = new ExcelListener<>(categoryMapper);
            //调用read方法读取excel数据
            EasyExcel.read(file.getInputStream(),
                    CategoryExcelVo.class,
                    excelListener).sheet().doRead();
        } catch (IOException e) {
            throw new BaseException(ResultCodeEnum.DATA_ERROR);
        }
    }
}
