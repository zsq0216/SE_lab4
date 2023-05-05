import { RouteRecordRaw } from 'vue-router';

const routes: RouteRecordRaw[] = [
  {
    // 小球开场动画
    path: '/',
    component: () => import('src/pages/outlookORfunctional/ballPage.vue'),
  },
  {
    // 登录页面
    name : 'login',
    path: '/login',
    component: () => import('src/pages/outlookORfunctional/LoginView.vue'),
  },

  // 商户相关页面
  {
    // 商户主页面
    path: '/merchantpage',
    component: () => import('src/pages/merchant/MerchantNew.vue'),
    meta: { requiresAuth: true } // 添加 requiresAuth 属性
  },

  {
    // 商户单个商店
    path: '/merchantshop',
    component: () => import('src/pages/shop/merchantShop.vue'),
  },

  // 用户相关页面
  {
    // 用户主页面
    path: '/user',
    component: () => import('src/pages/user/UserPage.vue'),
    meta: { requiresAuth: true } // 添加 requiresAuth 属性
  },
  {
    // 用户购物车页面
    path: '/userShoppingCart',
    component: () => import('src/pages/user/ShoppingCart.vue'),
  },
  {
    // 用户查看单个商店
    path: '/shop',
    component: () => import('src/pages/shop/shopPage.vue'),
  },

  // 统一账户页面
  // TODO：账户设置链接到此页面
  {
    path: '/accountInfo',
    component: () => import('src/pages/universal/PersonalMainPage.vue'),
  },

  // 管理员相关页面
  {
    // 管理员主页面
    path: '/admin',
    component: () => import('src/pages/admin/adminPage.vue'),
  },
  {
    // 管理员登录页面
    path: '/adminLogin',
    component: () => import('src/pages/admin/adminLoginPage.vue'),
  },
  {
    // ？
    path: '/admin/pass',
    component: () => import('src/pages/admin/PassPage.vue'),
  },
  {
    // ？
    path: '/admin/reject',
    component: () => import('src/pages/admin/rejectPage.vue'),
  },

  // 其他页面
  {
    // 联系我们
    path: '/contact',
    component: () => import('src/pages/outlookORfunctional/ContactPage.vue'),
  },
  {
    // 帮助
    path: '/help',
    component: () => import('src/pages/outlookORfunctional/HelpPage.vue'),
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '/:catchAll(.*)*',
    component: () => import('src/pages/outlookORfunctional/ErrorNotFound.vue'),
  },
];

export default routes;
