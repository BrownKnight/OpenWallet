<template>
  <b-container v-if="!account">
    <b-row>
      <h3>Account not found</h3>
    </b-row>
  </b-container>

  <b-container v-else>
    <b-row class="justify-content-between mt-4">
      <h3 class="text-left d-inline-block m-0">{{ account.name }}</h3>
      <b-button variant="outline-info" size="sm" v-b-modal.import-transactions-modal>Import Transactions</b-button>
    </b-row>

    <b-modal id="import-transactions-modal" title="Transaction Import" hide-footer>
      <b-tabs>
        <b-tab title="Import by File">
          <b-col cols="12">
            <TransactionImport />
          </b-col>
        </b-tab>
        <b-tab title="Generate">
          <b-col cols="12">
            <MockTransactionImport />
          </b-col>
        </b-tab>
      </b-tabs>
    </b-modal>

    <b-row>
      <AccountCard :account="account" />
    </b-row>

    <b-row>
      <h3>Transactions</h3>
    </b-row>
    <b-row>
      <b-col cols="12">
        <TransactionList :transactions="account.transactions" />
      </b-col>
    </b-row>
  </b-container>
</template>

<script lang="ts">
import { BaseComponent } from "@/components/BaseComponent";
import { Component, Prop } from "vue-property-decorator";
import AccountCard from "@/components/AccountCard.vue";
import TransactionImport from "@/components/import/TransactionImport.vue";
import MockTransactionImport from "@/components/import/MockTransactionImport.vue";
import TransactionList from "@/components/transaction/TransactionList.vue";
import { Account } from "@/data/models/Account";

@Component({ components: { AccountCard, TransactionImport, MockTransactionImport, TransactionList } })
export default class AccountDetails extends BaseComponent {
  @Prop({ required: true })
  id!: number;

  account: Account | null = null;

  async created() {
    await this.getAccount();
  }

  async getAccount() {
    this.account = await this.dataApi.accountApi.getAccountById(this.id);
    if (!this.account) {
      await this.$router.push("/wallet");
      this.showMessage({ message: "Error occurred. Could not fetch account.", variant: "danger" });
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss"></style>
