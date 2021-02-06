<template>
  <b-list-group>
    <b-list-group-item v-if="transactions == null || transactions.length == 0">
      No Transactions :(
    </b-list-group-item>
    <TransactionRow v-else v-for="transaction in transactions" :key="transaction.id" />
  </b-list-group>
</template>

<script lang="ts">
import { Component, Prop } from "vue-property-decorator";
import { BaseComponent } from "@/components/BaseComponent.ts";
import { Account } from "@/data/models/Account.ts";
import TransactionRow from "@/components/transaction/TransactionRow.vue";

@Component({ components: { TransactionRow } })
export default class TransactionImport extends BaseComponent {
  getAccounts = this.dataApi.accountApi.getAllAccounts.bind(this.dataApi.accountApi);
  accountTextFunction = (entity: Account) => entity.name;

  @Prop({ default: undefined })
  transactions: number | undefined;
}
</script>

<style scoped lang="scss"></style>
