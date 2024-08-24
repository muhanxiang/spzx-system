import request from '@/utils/request'

const api_name = '/admin/product/brand'

// 分页列表
export const GetBrandPageList = (page, limit) => {
  return request({
    url: `${api_name}/${page}/${limit}`,
    method: 'get'
  })
}

export const UpdateBrandById = brand => {
  return request({
    url: `${api_name}/updateById`,
    method: 'put',
    data: brand,
  })
}

// 保存品牌
export const SaveBrand = brand => {
    return request({
        url: `${api_name}/save`,
        method: 'post',
        data: brand,
    })
}

// 查询所有的品牌数据
export const FindAllBrand = () => {
  return request({
    url: `${api_name}/findAll`,
    method: 'get',
  })
}

// 根据id删除品牌
export const DeleteBrandById = id => {
  return request({
    url: `${api_name}/deleteById/${id}`,
    method: 'delete',
  })
}