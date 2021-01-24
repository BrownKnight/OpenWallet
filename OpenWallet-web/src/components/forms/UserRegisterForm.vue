<template>
  <b-form @submit.prevent="register">
    <LabelledInput
      autocomplete="given-name"
      id="first-name"
      label="First Name"
      type="text"
      v-model="registration.firstName"
    />

    <LabelledInput
      autocomplete="family-name"
      id="last-name"
      label="Last Name"
      type="text"
      v-model="registration.lastName"
    />

    <LabelledInput
      autocomplete="email"
      id="email"
      label="Email Address"
      type="email"
      v-model="registration.emailAddress"
    />

    <LabelledInput
      autocomplete="new-password"
      id="password"
      label="Password"
      type="password"
      v-model="registration.password"
    />

    <b-button class="mt-2" variant="success" type="submit">Register</b-button>
  </b-form>
</template>

<script lang="ts">
import { Component } from "vue-property-decorator";
import { BaseComponent } from "@/components/BaseComponent.ts";
import { UserRegistration, UserRegistrationRequest } from "@/data/models/UserRegistration.ts";
import LabelledInput from "@/components/util/LabelledInput.vue";

@Component({ components: { LabelledInput } })
export default class UserRegisterForm extends BaseComponent {
  registration: UserRegistration = { emailAddress: "", password: "", firstName: "", lastName: "" };

  async register() {
    const success = await this.dataApi.userApi.register(new UserRegistrationRequest(this.registration));
    if (success) {
      this.$emit("showLogin");
    }
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
