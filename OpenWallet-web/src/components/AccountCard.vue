<template>
  <b-card no-body class="d-flex">
    <b-row>
      <b-col class="text-left mx-2">
        <h5>{{ account.name }}</h5>
      </b-col>
      <b-col class="text-right mx-2">£{{ account.balance }}</b-col>
    </b-row>

    <b-link :to="`/wallet/account/${account.id}`">View Details</b-link>
  </b-card>
</template>

<script lang="ts">
import { Component, Prop } from "vue-property-decorator";
import { BaseComponent } from "@/components/BaseComponent";
import { Account } from "@/data/models/Account";

@Component
export default class AccountCard extends BaseComponent {
  @Prop({ required: true })
  account!: Account;
}
</script>

<style scoped lang="scss"></style>
