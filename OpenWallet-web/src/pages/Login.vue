<template>
  <b-card class="mt-3" no-body>
    <b-tabs justified card v-model="activeTab">
      <b-tab title="Login" active><LoginForm /></b-tab>
      <b-tab title="Register"><UserRegisterForm @showLogin="activeTab = 0"/></b-tab>
    </b-tabs>
  </b-card>
</template>

<script lang="ts">
import { BaseComponent } from "@/components/BaseComponent";
import { Component } from "vue-property-decorator";
import LoginForm from "@/components/forms/LoginForm.vue";
import UserRegisterForm from "@/components/forms/UserRegisterForm.vue";

@Component({ components: { LoginForm, UserRegisterForm } })
export default class Home extends BaseComponent {
  activeTab = 0;
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
