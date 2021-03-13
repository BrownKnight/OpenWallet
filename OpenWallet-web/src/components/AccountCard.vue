<template>
  <b-list-group-item :to="`/wallet/account/${account.id}`" class="no-link-style" active-class="">
    <div class="d-flex align-items-center p-2">
      <img class="institution-logo rounded" :src="account.institution.iconUrl" :alt="account.name + ' Logo'" />
      <h5 class="my-0 ml-2 nr-auto">{{ account.name }}</h5>
      <span class="ml-auto mr-0">{{ accountBalance }}</span>
    </div>
  </b-list-group-item>
</template>

<script lang="ts">
import { Component, Prop } from "vue-property-decorator";
import { BaseComponent } from "@/components/BaseComponent";
import { Account } from "@/data/models/Account";
import { Currency } from "@/data/models/Currency";

@Component
export default class AccountCard extends BaseComponent {
  @Prop({ required: true })
  account!: Account;

  get currencySymbol() {
    return Currency.getSymbol(this.account.currency);
  }

  get accountBalance() {
    if (this.account.balance >= 0) {
      return `${this.currencySymbol}${this.account.balance.toFixed(2)}`;
    } else {
      return `-${this.currencySymbol}${Math.abs(this.account.balance).toFixed(2)}`;
    }
  }
}
</script>

<style scoped lang="scss">
.institution-logo {
  height: 2em;
  width: 2em;
}
</style>
