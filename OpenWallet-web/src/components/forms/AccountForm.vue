<template>
  <b-form @submit.prevent="addAccount">
    <LabelledInput id="name" label="Account Name" type="text" v-model="account.name" />

    <EntitySelect
      id="institution"
      label="Institution"
      :optionsFunction="getInstitutions"
      :optionTextFunction="institutionTextFunction"
      v-model="account.institution.id"
    />

    <b-button class="mt-2" variant="success" type="submit">Add Account</b-button>
  </b-form>
</template>

<script lang="ts">
import { Component } from "vue-property-decorator";
import { BaseComponent } from "@/components/BaseComponent.ts";
import { NewAccountRequest } from "@/data/models/Account.ts";
import LabelledInput from "@/components/util/LabelledInput.vue";
import EntitySelect from "@/components/util/EntitySelect.vue";
import { Institution } from "@/data/models/Institution";

@Component({ components: { LabelledInput, EntitySelect } })
export default class AccountForm extends BaseComponent {
  account: NewAccountRequest = { name: "", institution: { id: 0 } };
  getInstitutions = this.dataApi.institutionApi.getAllInstitutions.bind(this.dataApi.institutionApi);
  institutionTextFunction = (entity: Institution) => entity.name;

  // async addAccount() {
  //   const success = await this.dataApi.accountApi.addAccount(account);
  //   if (success) {
  //     this.$emit("success");
  //   }
  // }
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
