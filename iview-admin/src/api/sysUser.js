import axios from '@/libs/api.request'
import * as constData from '@/libs/const-data'

// 获取用户列表
export const getUserList = ({ account, userName, deptId, pageNo, pageSize }) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-user',
    params: {
      account,
      userName,
      deptId,
      pageNo,
      pageSize
    },
    method: 'get'
  })
}

// 查询用户设置的菜单信息
export const getListUserMenu = (userId) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-user/list-user-menu-map/' + userId,
    method: 'get'
  })
}

// 查询用户菜单部件权限
export const getMenuPartAuth = (menuId) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-user/menu-part-auth/' + menuId,
    method: 'get'
  })
}

// 批量删除用户
export const removeUserInfo = (params) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-user/remove',
    data: params,
    method: 'post'
  })
}

// 保存更新用户信息
export const addAndUpdateUserInfo = (params) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-user/save-or-update-user',
    data: params,
    method: 'post'
  })
}

// 根据userId获取用户信息
export const getUserInfoDetail = (userId) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-user/' + userId,
    method: 'get'
  })
}

// 配置用户菜单权限
export const updateMenuAuth = (params) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-user/update-menu-auth',
    data: params,
    method: 'post'
  })
}

// 查询用户设置的菜单部件权限
export const getMenuPartAuthorize = ({ menuId, userId }) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-user/user-menu-part-auth',
    params: {
      menuId,
      userId
    },
    method: 'get'
  })
}

// 配置用户菜单部件权限
export const updateMenuPartAuth = (params) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-user/update-menu-part-auth',
    data: params,
    method: 'post'
  })
}
