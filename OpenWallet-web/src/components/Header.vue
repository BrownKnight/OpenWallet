<template>
  <b-navbar variant="primary" type="dark" fixed="top" toggleable="md">
    <b-navbar-brand to="index">
      <!-- <img src="@/assets/bot.svg" class="logo align-top" /> -->
      Open Wallet
    </b-navbar-brand>
    <b-navbar-toggle target="navbar-collapse"></b-navbar-toggle>
    <b-collapse id="navbar-collapse" is-nav>
      <b-navbar-nav v-if="isLoggedIn">
        <b-nav-item v-if="isAdmin()" to="users">Users</b-nav-item>
        <b-nav-item to="users">Wallet</b-nav-item>
      </b-navbar-nav>

      <b-navbar-nav class="ml-auto">
        <b-nav-item-dropdown right>
          <template v-slot:button-content>
            <b-icon icon="person-circle" size="sm" variant="light" class="align-top"></b-icon>
            <span class="ml-2">{{ userFullName }}</span>
          </template>
          <b-dropdown-item to="my-account">My Account</b-dropdown-item>
          <b-dropdown-item @click="invalidateToken()">Logout</b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
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
