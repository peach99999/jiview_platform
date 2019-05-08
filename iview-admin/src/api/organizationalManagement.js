import axios from '@/libs/api.request'
import * as constData from '@/libs/const-data';

export const getDepartmentList = (params) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-dept',
    params: params,
    method: 'get'
  });
};
