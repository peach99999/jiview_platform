import axios from '@/libs/api.request'
import * as constData from '@/libs/const-data'

// 获取角色列表
export const getRoleList = ({ roleName, deptId, pageNo, pageSize }) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-role',
    params: {
      roleName,
      deptId,
      pageNo,
      pageSize
    },
    method: 'get'
  })
}

// 查询全部有效角色
export const getAllValidRoles = () => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-role/all-valid-roles',
    method: 'get'
  })
}

// 删除角色
export const removeRoleInfo = (params) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-role/remove',
    data: params,
    method: 'post'
  })
}

// 保存更新角色
export const updateRoleInfo = (params) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-role/save-or-update',
    data: params,
    method: 'post'
  })
}

// 更新菜单权限
export const updateMenuAuthorize = (params) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-role/update-menu-auth',
    data: params,
    method: 'post'
  })
}

// 更新菜单部件权限
export const updateMenuPartAuthorize = (params) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-role/update-menu-part-auth',
    data: params,
    method: 'post'
  })
}

// 查询角色设置的菜单部件权限
export const getMenuPartAuthorize = ({ menuId, roleId }) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-role/role-menu-part-auth',
    params: {
      menuId,
      roleId
    },
    method: 'get'
  })
}

// 获取单条角色
export const getRoleInfo = (roleId) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-role/' + roleId,
    method: 'get'
  })
}
