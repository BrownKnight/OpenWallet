import Vue from "vue";
import App from "./App.vue";

import VueRouter, { RouteConfig } from "vue-router";
import Vuex from "vuex";

import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

import { Store } from "@/authStore.ts";

import Home from "@/pages/Home.vue";

Vue.use(VueRouter);
Vue.use(BootstrapVue);
Vue.use(IconsPlugin);
Vue.use(Vuex);

const routes: RouteConfig[] = [
  { path: "/index", component: Home },
  { path: "/", redirect: "/index" },
  { path: "/.well-known/change-password", redirect: "/my-account" }
];

const router = new VueRouter({
  mode: "history",
  routes: routes
});

router.beforeEach((to, _from, next) => {
  const publicPages = ["/login"];
  const authRequired = !publicPages.includes(to.path);
  const loggedIn = !!Store.state.AuthModule.token;

  // trying to access a restricted page + not logged in
  // redirect to login page
  if (authRequired && !loggedIn) {
    sessionStorage.setItem("redirectTo", to.path);
    next(`/login`);
  } else {
    next();
  }
});

Vue.config.productionTip = false;

new Vue({
  render: h => h(App),
  router: router,
  store: Store
}).$mount("#app");
