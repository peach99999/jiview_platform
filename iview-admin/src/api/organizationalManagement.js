import axios from '@/libs/api.request'
import * as constData from '@/libs/const-data';

//部门管理列表
export const getDepartmentList = (params) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-dept',
    params: params,
    method: 'get'
  });
};
//批量删除部门
export const deleteDepartmentList = (params) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-dept/remove',
    method: 'post',
    data: params
  });
};
//新增部门供选择下拉部门列表
export const getSelectDepartmentList = () => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-dept/dept-list',
    method: 'get'
  });
};
