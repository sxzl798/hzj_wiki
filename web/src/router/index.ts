import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import HomeView from '../views/homeView.vue'
import AboutView from "@/views/aboutView.vue";
import AdminUser from "@/views/admin/admin-user.vue";
import AdminEbook from "@/views/admin/admin-ebook.vue";
import AdminCategory from "@/views/admin/admin-category.vue";
import AdminDoc from "@/views/admin/admin-doc.vue";
import DocView from "@/views/docView.vue";
import {Tool} from "@/util/tool";
import store from "@/store";

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/doc',
    name: 'Doc',
    component: DocView
  },
  {
    path: '/about',
    name: 'about',
    component: AboutView
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    // component: () => import(/* webpackChunkName: "about" */ '../views/aboutView.vue')
  },
  {
    path: '/admin/user',
    name: 'AdminUser',
    component: AdminUser,
    meta:{
      loginRequire: true
    }
  },
  {
    path: '/admin/ebook',
    name: 'AdminEbook',
    component: AdminEbook,
    meta:{
      loginRequire: true
    }
  },
  {
    path: '/admin/category',
    name: 'AdminCategory',
    component: AdminCategory,
    meta:{
      loginRequire: true
    }

  },
  {
    path: '/admin/doc',
    name: 'AdminDoc',
    component: AdminDoc,
    meta:{
      loginRequire: true
    }
  },

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

//路由登录拦截
router.beforeEach((to,from,next) => {
  //要不要对meta.logRequire属性做监控拦截
  if (to.matched.some(function (item){
    console.log(item,"是否需要登录校验：",item.meta.loginRequire);
    return item.meta.loginRequire
  })){
    const loginUser = store.state.user;
    if (Tool.isEmpty(loginUser)){
      console.log("用户未登录");
      next('/');
    }else {
      next();
    }
  }else {
    next();
  }
});

export default router
