<template>
  <b-form @submit.prevent="importTransactions">
    <!-- Only displayed if a account id is not supplied to the form -->
    <EntitySelect
      class="mt-2"
      id="account"
      label="Account"
      :optionsFunction="getAccounts"
      :optionTextFunction="accountTextFunction"
      v-model="selectedAccountId"
      v-if="accountId !== null"
    />

    <b-form-file
      class="mt-2"
      v-model="importFile"
      :state="Boolean(importFile)"
      placeholder="Choose a file or drop it here..."
      drop-placeholder="Drop file here..."
      accept="*.csv"
    ></b-form-file>

    <b-button class="mt-2" variant="success" type="submit">Import</b-button>
  </b-form>
</template>

<script lang="ts">
import { Component, Prop } from "vue-property-decorator";
import { BaseComponent } from "@/components/BaseComponent";
import { Account } from "@/data/models/Account";
import LabelledInput from "@/components/util/LabelledInput.vue";
import EntitySelect from "@/components/util/EntitySelect.vue";

@Component({ components: { LabelledInput, EntitySelect } })
export default class TransactionImport extends BaseComponent {
  getAccounts = this.dataApi.accountApi.getAllAccounts.bind(this.dataApi.accountApi);
  accountTextFunction = (entity: Account) => entity.name;

  @Prop({ default: undefined })
  accountId: number | undefined;

  selectedAccountId: number | null = null;
  importFile: File | null = null;
}
</script>

<style scoped lang="scss"></style>
