<template>
  <LoginForm />
</template>

<script lang="ts">
import { BaseComponent } from "@/components/BaseComponent";
import { Component } from "vue-property-decorator";
import LoginForm from "@/components/forms/LoginForm.vue";

@Component({ components: { LoginForm } })
export default class Home extends BaseComponent {}
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
