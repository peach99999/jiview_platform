import axios from '@/libs/api.request'
import * as constData from '@/libs/const-data'

// 获取code列表
export const getCodeList = (codeType) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-code',
    params: {
      codeType
    },
    method: 'get'
  })
}

// 查询角色设置的菜单部件权限
export const getCodeByTypeAndKey = ({ codeKey, codeType }) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-code/get-by-type-and-key',
    params: {
      codeKey,
      codeType
    },
    method: 'get'
  })
}
