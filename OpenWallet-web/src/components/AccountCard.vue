<template>
  <b-card :header="account.name">
    {{ account.balance }}
    <b-link :to="`/wallet/account/${account.id}`">View Details</b-link>
  </b-card>
</template>

<script lang="ts">
import { Component, Prop } from "vue-property-decorator";
import { BaseComponent } from "@/components/BaseComponent.ts";
import { Account } from "@/data/models/Account.ts";

@Component
export default class AccountCard extends BaseComponent {
  @Prop({ required: true })
  account!: Account;
}
</script>

<style scoped lang="scss"></style>
