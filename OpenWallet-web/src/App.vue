<template>
  <div id="app">
    <Header v-if="isLoggedIn" />
    <b-badge v-if="!isProductionBuild" variant="danger" style="border-radius: 0;">DEVELOPMENT</b-badge>
    <b-container class="content">
      <router-view></router-view>
    </b-container>
    <Footer />
  </div>
</template>

<script lang="ts">
import { Component } from "vue-property-decorator";
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import { BaseComponent } from "@/components/BaseComponent";

@Component({ components: { Header, Footer } })
export default class App extends BaseComponent {
  get isProductionBuild() {
    return process.env.NODE_ENV === "production";
  }
}
</script>

<style lang="scss">
$enable-shadows: true;
$enable-gradients: true;
$theme-colors: (
  "primary": #2b6eb7,
  "light": #e1e3df,
  "dark": #17161f,
  "info": #2b6eb7,
  "success": #50af72,
  "warning": #ffc107,
  "danger": #f44336
);

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 56px;
  display: flex;
  flex-direction: column;
  flex: 1;
}

html,
body {
  height: 100%;
  scroll-behavior: smooth;
}
body {
  display: flex;
  flex-direction: column;
}
.content {
  flex: 1 0 auto;
}
.b-toaster-top-right {
  top: 56px !important;
}

.toast-body {
  @extend .shadow-lg;
  text-decoration: none !important;
  color: inherit !important;
}

.nav-pills {
  @extend .shadow-sm;
  @extend .rounded;
}

.router-link-active {
  color: rgba(255, 255, 255, 0.85) !important;
}

a,
button {
  font-weight: normal !important;
}
// Import Bootstrap and BootstrapVue source SCSS files
@import "~bootstrap/scss/bootstrap.scss";
@import "~bootstrap-vue/src/index.scss";
</style>
