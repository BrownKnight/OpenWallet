<template>
  <b-form @submit.prevent="login">
    <LabelledInput
      autocomplete="email"
      id="email"
      label="Email Address"
      type="email"
      v-model="registration.emailAddress"
    />

    <LabelledInput
      autocomplete="email"
      id="email"
      label="Email Address"
      type="email"
      v-model="registration.emailAddress"
    />

    <b-button class="ml-2 my-2" variant="success" type="submit">Register</b-button>
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
