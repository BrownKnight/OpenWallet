<template>
  <b-list-group-item> {{ transaction.description }}: {{ transaction.amount }}</b-list-group-item>
</template>

<script lang="ts">
import { Component, Prop } from "vue-property-decorator";
import { BaseComponent } from "@/components/BaseComponent";
import { Transaction } from "@/data/models/Transaction";
import LabelledInput from "@/components/util/LabelledInput.vue";
import EntitySelect from "@/components/util/EntitySelect.vue";

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
}
</script>

<style scoped lang="scss"></style>
