<template>
  <b-form @submit.prevent="login">
    <label class="sr-only" id="input-label-email" for="input-email">Email</label>
    <b-input-group class="mr-2 my-3" prepend="Email">
      <b-form-input
        id="input-email"
        v-model="emailAddress"
        type="email"
        required
        autocomplete="username"
        autofocus
      ></b-form-input>
    </b-input-group>

    <label class="sr-only" id="input-label-password" for="input-password">Password</label>
    <b-input-group class="mt-4 mb-3" prepend="Password">
      <b-form-input
        id="input-password"
        v-model="password"
        type="password"
        autocomplete="current-password"
        required
      ></b-form-input>
    </b-input-group>

    <b-button class="ml-2 my-2" variant="success" type="submit">Login</b-button>
  </b-form>
</template>

<script lang="ts">
import { Component } from "vue-property-decorator";
import { BaseComponent } from "@/components/BaseComponent.ts";

@Component
export default class Header extends BaseComponent {
  get userFullName() {
    return this.$store.state?.AuthModule?.userData?.username ?? "Not Logged In";
  }

  get isLoggedIn() {
    return this.$store.state?.AuthModule?.token !== null;
  }

  invalidateToken() {
    this.$store.commit("invalidateToken");
    this.$router.push("/login");
  }
}
</script>

<style scoped lang="scss">
.navbar-dark .navbar-toggler {
  border-color: transparent;
}

.logo {
  color: white;
  filter: invert(90%);
  height: 1.4em;
}
</style>
