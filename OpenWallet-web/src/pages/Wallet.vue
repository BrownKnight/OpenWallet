<template>
  <b-row class="justify-content-between mt-4">
    <h3 class="text-left d-inline-block m-0">Wallet</h3>
    <b-button variant="outline-info" size="sm">Add Account</b-button>
  </b-row>
</template>

<script lang="ts">
import { BaseComponent } from "@/components/BaseComponent";
import { Component } from "vue-property-decorator";
import LoginForm from "@/components/forms/LoginForm.vue";
import UserRegisterForm from "@/components/forms/UserRegisterForm.vue";

@Component({ components: { LoginForm, UserRegisterForm } })
export default class Wallet extends BaseComponent {
  activeTab = 0;
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss"></style>
