import { route } from 'quasar/wrappers';
import {
  createMemoryHistory,
  createRouter,
  createWebHashHistory,
  createWebHistory,
} from 'vue-router';
import routes from './routes';
import { useStore } from 'src/store'
import store from 'src/store'

/*
 * If not building with SSR mode, you can
 * directly export the Router instantiation;
 *
 * The function below can be async too; either use
 * async/await or return a Promise which resolves
 * with the Router instance.
 */

export default route(function ({ store }) {
  const createHistory = process.env.SERVER
    ? createMemoryHistory
    : (process.env.VUE_ROUTER_MODE === 'history' ? createWebHistory : createWebHashHistory);

  const Router = createRouter({
    scrollBehavior: () => ({ left: 0, top: 0 }),
    routes,

    // Leave this as is and make changes in quasar.conf.js instead!
    // quasar.conf.js -> build -> vueRouterMode
    // quasar.conf.js -> build -> publicPath
    history: createHistory(
      process.env.MODE === 'ssr' ? void 0 : process.env.VUE_ROUTER_BASE
    ),
  });

  Router.beforeEach((to, from, next) => {
    //const store = useStore();
    const userId = Number(localStorage.getItem('userId'));
    const isAuthenticated = !isNaN(userId) && userId !== 0;
    console.log('userId', userId)
    console.log('isAuthenticated', isAuthenticated);
    if (to.matched.some(record => record.meta.requiresAuth) && !isAuthenticated) {
      console.log('not authenticated');
      next({ name: 'login' });
    } else {
      next();
    }
  });

  return Router;
});


