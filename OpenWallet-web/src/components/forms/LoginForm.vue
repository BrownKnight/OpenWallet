<template>
  <b-form @submit.prevent="login">
    <LabelledInput
      autocomplete="username"
      id="username"
      label="Email Address"
      type="email"
      v-model="authRequest.username"
      required
    />

    <LabelledInput
      autocomplete="current-password"
      id="password"
      label="Password"
      type="password"
      v-model="authRequest.password"
      required
    />

    <b-button class="ml-2 my-2" variant="success" type="submit">Login</b-button>
  </b-form>
</template>

<script lang="ts">
import { Component } from "vue-property-decorator";
import { BaseComponent } from "@/components/BaseComponent.ts";
import { AuthRequest } from "@/data/models/AuthRequest";
import LabelledInput from "@/components/util/LabelledInput.vue";

@Component({ components: { LabelledInput } })
export default class LoginForm extends BaseComponent {
  authRequest: AuthRequest = { username: "", password: "" };

  login() {
    this.dataApi.userApi.login(this.authRequest);
  }
}
</script>

<style scoped lang="scss"></style>
