import axios from '@/libs/api.request'
import * as constData from '@/libs/const-data'

export const getMenuPartAuth = (menuId) => {
  return axios.request({
    url: constData.API_BEGIN_POINT + '/sys-user/menu-part-auth/' + menuId,
    method: 'get'
  })
}

// export const login = ({ userName, password }) => {
//   const loginParam = {
//     userLogin:userName,
//     userPwd:password
//   }
//   return axios.request({
//     url: constData.API_BEGIN_POINT + '/login',
//     // url: 'login',
//     data:loginParam,
//     method: 'post'
//   })
// }

// export const getUserInfo = (token) => {
//   return axios.request({
//     url: 'get_info',
//     params: {
//       token
//     },
//     method: 'get'
//   })
// }

// export const logout = (token) => {
//   return axios.request({
//     url: 'logout',
//     method: 'post'
//   })
// }

// export const getUnreadCount = () => {
//   return axios.request({
//     url: 'message/count',
//     method: 'get'
//   })
// }

// export const getMessage = () => {
//   return axios.request({
//     url: 'message/init',
//     method: 'get'
//   })
// }

// export const getContentByMsgId = msg_id => {
//   return axios.request({
//     url: 'message/content',
//     method: 'get',
//     params: {
//       msg_id
//     }
//   })
// }

// export const hasRead = msg_id => {
//   return axios.request({
//     url: 'message/has_read',
//     method: 'post',
//     data: {
//       msg_id
//     }
//   })
// }
//
// export const removeReaded = msg_id => {
//   return axios.request({
//     url: 'message/remove_readed',
//     method: 'post',
//     data: {
//       msg_id
//     }
//   })
// }
//
// export const restoreTrash = msg_id => {
//   return axios.request({
//     url: 'message/restore',
//     method: 'post',
//     data: {
//       msg_id
//     }
//   })
// }
