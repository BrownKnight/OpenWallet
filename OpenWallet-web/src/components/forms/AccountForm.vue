<template>
  <b-form @submit.prevent="saveAccount">
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
import { Component, Prop } from "vue-property-decorator";
import { BaseComponent } from "@/components/BaseComponent.ts";
import { Account } from "@/data/models/Account.ts";
import LabelledInput from "@/components/util/LabelledInput.vue";
import EntitySelect from "@/components/util/EntitySelect.vue";
import { Institution } from "@/data/models/Institution";

@Component({ components: { LabelledInput, EntitySelect } })
export default class AccountForm extends BaseComponent {
  getInstitutions = this.dataApi.institutionApi.getAllInstitutions.bind(this.dataApi.institutionApi);
  institutionTextFunction = (entity: Institution) => entity.name;

  async saveAccount() {
    const success = await this.dataApi.accountApi.saveAccount(this.account);
    if (success) {
      this.$emit("success");
    }
  }

  @Prop({
    default: () => {
      return { name: "", institution: { id: 0 } };
    }
  })
  value!: Account;

  get account() {
    return this.value;
  }

  set account(account) {
    this.$emit("input", account);
  }
}
</script>

<style scoped lang="scss"></style>
