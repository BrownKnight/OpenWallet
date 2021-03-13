<template>
  <b-list-group-item class="d-flex">
    <span class="ml-0 mr-auto text-left">{{ transaction.description }}</span>
    <span class="ml-auto mr-0"> {{ transactionAmount }} </span>
  </b-list-group-item>
</template>

<script lang="ts">
import { Component, Prop } from "vue-property-decorator";
import { BaseComponent } from "@/components/BaseComponent";
import { Transaction } from "@/data/models/Transaction";
import LabelledInput from "@/components/util/LabelledInput.vue";
import EntitySelect from "@/components/util/EntitySelect.vue";
import { Currency } from "@/data/models/Currency";

@Component({ components: { LabelledInput, EntitySelect } })
export default class TransactionImport extends BaseComponent {
  @Prop({
    default: () => {
      return { name: "", institution: { id: 0 } };
    }
  })
  value!: Transaction;

  get transaction() {
    return this.value;
  }

  set transaction(transaction) {
    this.$emit("input", transaction);
  }

  get currencySymbol() {
    return Currency.getSymbol(this.transaction.account.currency);
  }

  get transactionAmount() {
    if (this.transaction.amount >= 0) {
      return `${this.currencySymbol}${this.transaction.amount.toFixed(2)}`;
    } else {
      return `-${this.currencySymbol}${Math.abs(this.transaction.amount).toFixed(2)}`;
    }
  }
}
</script>

<style scoped lang="scss"></style>
