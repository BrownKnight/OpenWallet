<template>
  <b-container>
    <b-row class="justify-content-between mt-4">
      <h3 class="text-left d-inline-block m-0">Wallet</h3>
      <b-button variant="outline-info" size="sm" v-b-modal.add-account-modal>Add Account</b-button>
    </b-row>

    <b-modal id="add-account-modal" title="Add Account" hide-footer>
      <AccountForm />
    </b-modal>

    <b-row>
      <AccountCard v-for="account in accounts" :key="account.id" :account="account" />
    </b-row>
  </b-container>
</template>

<script lang="ts">
import { BaseComponent } from "@/components/BaseComponent";
import { Component } from "vue-property-decorator";
import AccountForm from "@/components/forms/AccountForm.vue";
import AccountCard from "@/components/AccountCard.vue";
import { Account } from "@/data/models/Account";

@Component({ components: { AccountForm, AccountCard } })
export default class Wallet extends BaseComponent {
  activeTab = 0;

  accounts: Account[] = [];

  async created() {
    await this.getAccounts();
  }

  async getAccounts() {
    this.accounts = await this.dataApi.accountApi.getAllAccounts();
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss"></style>
