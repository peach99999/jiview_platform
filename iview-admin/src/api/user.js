import axios from '@/libs/api.request'
import * as constData from '@/libs/const-data';

export const login = ({ userName, password }) => {
  const loginParam = {
    account:userName,
    userPwd:password
  }
  return axios.request({
    url: constData.API_BEGIN_POINT + '/login',
    // url: 'login',
    data:loginParam,
    method: 'post'
  })
}

// 修改密码
export const changeUserPwd = (params) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/login/reset-pwd',
    data: params,
    method: 'post'
  });
};
