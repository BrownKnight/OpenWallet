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

    <b-button @click="generateTransactions">Generate Transactions</b-button>

    <TransactionList :transactions="transactions" />

    <b-button class="mt-2" variant="success" type="submit">Import</b-button>
  </b-form>
</template>

<script lang="ts">
import { Component, Prop } from "vue-property-decorator";
import { BaseComponent } from "@/components/BaseComponent";
import { Account } from "@/data/models/Account";
import { Transaction } from "@/data/models/Transaction";
import LabelledInput from "@/components/util/LabelledInput.vue";
import EntitySelect from "@/components/util/EntitySelect.vue";
import TransactionList from "@/components/transaction/TransactionList.vue";

@Component({ components: { LabelledInput, EntitySelect, TransactionList } })
export default class TransactionImport extends BaseComponent {
  getAccounts = this.dataApi.accountApi.getAllAccounts.bind(this.dataApi.accountApi);
  accountTextFunction = (entity: Account) => entity.name;

  @Prop({ default: undefined })
  accountId: number | undefined;

  selectedAccountId: number | null = null;
  transactions: Transaction[] | null = null;

  get finalAccountId(): number | null {
    return this.accountId ?? this.selectedAccountId;
  }

  generateTransactions() {
    if (this.finalAccountId) {
      this.transactions = this.dataApi.transactionApi.generateTestTransactions(10, this.finalAccountId);
    }
  }

  async importTransactions() {
    if (this.finalAccountId && this.transactions) {
      const res = await this.dataApi.accountApi.addTransactions(this.finalAccountId, this.transactions);
      if (res) {
        this.transactions = res;
      } else {
        this.showMessage({ message: "No transactions returned after import" });
      }
    }
  }
}
</script>

<style scoped lang="scss"></style>
