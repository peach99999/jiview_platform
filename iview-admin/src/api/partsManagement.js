import axios from '@/libs/api.request'
import * as constData from '@/libs/const-data'

// 获取菜单部件详情
export const getMenuPartDetail = (menuId) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-menu-part/' + menuId,
    method: 'get'
  })
}

//新增或修改菜单部件
export const saveOrUpdateMenuPart = (params) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-menu-part/save-or-update-menu-part',
    data: params,
    method: 'post'
  })
}
