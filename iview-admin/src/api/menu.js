import axios from '@/libs/api.request'
import * as constData from '@/libs/const-data'

// 获取菜单列表
export const list = () => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-menu',
    method: 'get'
  })
}

// 获取用户菜单列表
export const listUserMenuTree = () => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-menu/user-menu-tree',
    method: 'get'
  })
}

// 获取菜单树
export const listMenuTree = () => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-menu/menu-tree',
    method: 'get'
  })
}

// 新增菜单
export const save = (params) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-menu',
    data: params,
    method: 'post'
  })
}

// 更新菜单信息
export const update = (params) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-menu/' + params.menuId,
    data: params,
    method: 'put'
  })
}

// 删除菜单
export const remove = (params) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-menu/remove',
    data: params,
    method: 'post'
  })
}
