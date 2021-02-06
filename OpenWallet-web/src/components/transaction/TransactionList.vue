<template>
  <b-list-group>
    <b-list-group-item v-if="transactions == null || transactions.length == 0">
      No Transactions :(
    </b-list-group-item>
    <TransactionRow v-else v-for="transaction in transactions" :key="transaction.id" :value="transaction" />
  </b-list-group>
</template>

<script lang="ts">
import { Component, Prop } from "vue-property-decorator";
import { BaseComponent } from "@/components/BaseComponent.ts";
import TransactionRow from "@/components/transaction/TransactionRow.vue";

@Component({ components: { TransactionRow } })
export default class TransactionList extends BaseComponent {
  @Prop({ default: undefined })
  transactions: number | undefined;
}
</script>

<style scoped lang="scss"></style>
