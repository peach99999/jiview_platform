import Main from '@/components/main'
import parentView from '@/components/parent-view'

/**
 * iview-admin中meta除了原生参数外可配置的参数:
 * meta: {
 *  title: { String|Number|Function }
 *         显示在侧边栏、面包屑和标签栏的文字
 *         使用'{{ 多语言字段 }}'形式结合多语言使用，例子看多语言的路由配置;
 *         可以传入一个回调函数，参数是当前路由对象，例子看动态路由和带参路由
 *  hideInBread: (false) 设为true后此级路由将不会出现在面包屑中，示例看QQ群路由配置
 *  hideInMenu: (false) 设为true后在左侧菜单不会显示该页面选项
 *  notCache: (false) 设为true后页面在切换标签后不会缓存，如果需要缓存，无需设置这个字段，而且需要设置页面组件name属性和路由配置的name一致
 *  access: (null) 可访问该页面的权限数组，当前路由设置的权限会影响子路由
 *  icon: (-) 该页面在左侧菜单、面包屑和标签导航处显示的图标，如果是自定义图标，需要在图标名称前加下划线'_'
 *  beforeCloseName: (-) 设置该字段，则在关闭当前tab页时会去'@/router/before-close.js'里寻找该字段名对应的方法，作为关闭前的钩子函数
 * }
 */

export const loginRouter = {
  path: '/login',
  name: 'login',
  meta: {
    title: 'Login - 登录',
    hideInMenu: true
  },
  component: () => import('@/view/login/login.vue')
};

export const page404 = {
  path: '*',
  name: 'error_404',
  meta: {
    hideInMenu: true,
    title: '404-页面不存在'
  },
  component: () => import('@/view/error-page/404.vue')
};

export const page401 = {
  path: '/401',
  name: 'error_401',
  meta: {
    hideInMenu: true,
    title: '401-页面不存在'
  },
  component: () => import('@/view/error-page/401.vue')
};

export const page500 = {
  path: '/500',
  name: 'error_500',
  meta: {
    hideInMenu: true,
    title: '500-服务端错误'
  },
  component: () => import('@/view/error-page/500.vue')
};

// 作为Main组件的子页面展示并且在左侧菜单显示的路由写在appRouter里
export const appRouter = [{
  path: '/management',
  name: 'management',
  meta: {
    title: '系统管理'
  },
  component: Main,
  children: [
    {
      path: 'organizationalManagement',
      name: 'organizationalManagement',
      meta: {
        // icon: 'md-notifications',
        title: '组织机构管理'
      },
      component: () => import('@/pages/organizationalManagement/organizationalManagement.vue')
    },
    {
      path: 'menu',
      name: 'menu',
      meta: {
        // icon: 'md-notifications',
        title: '菜单管理'
      },
      component: () => import('@/pages/system/menu.vue')
    }
  ]
}];
// 作为Main组件的子页面展示但是不在左侧菜单显示的路由写在otherRouter里
export const otherRouter = {
  path: '/',
  name: '_home',
  redirect: '/home',
  component: Main,
  meta: {
    hideInMenu: true,
    notCache: true
  },
  children: [
    {
      path: '/home',
      name: 'home',
      meta: {
        hideInMenu: true,
        title: '首页',
        notCache: true,
        icon: 'md-home'
      },
      component: () => import('@/view/single-page/home')
    },
    {
      path: 'message_page',
      name: 'message_page',
      meta: {
        icon: 'md-notifications',
        title: '消息中心'
      },
      component: () => import('@/view/single-page/message/index.vue')
    },
    {
      path: 'params/:id',
      name: 'params',
      meta: {
        icon: 'md-flower',
        title: route => `{{ params }}-${route.params.id}`,
        notCache: true,
        beforeCloseName: 'before_close_normal'
      },
      component: () => import('@/view/argu-page/params.vue')
    },
    {
      path: 'query',
      name: 'query',
      meta: {
        icon: 'md-flower',
        title: route => `{{ query }}-${route.query.id}`,
        notCache: true
      },
      component: () => import('@/view/argu-page/query.vue')
    }
  ]
};

export const routers = [
  loginRouter,
  ...appRouter,
  otherRouter,
  page500,
  page401,
  page404
]

export default routers
