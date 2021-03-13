<template>
  <b-container v-if="!account">
    <b-row>
      <h3>Account not found</h3>
    </b-row>
  </b-container>

  <b-container v-else>
    <b-row class="mt-4">
      <AccountCard :account="account" class="m-0 flex-grow-1" />
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

    <b-row class="mt-4 mb-2">
      <h3 class="ml-0 mr-auto my-0">Transactions</h3>
      <b-dropdown variant="outline-info" size="sm" no-caret right class="ml-auto mr-0">
        <template #button-content> <b-icon icon="three-dots" /> </template>
        <b-dropdown-item v-b-modal.import-transactions-modal>Import Transactions</b-dropdown-item>
        <b-dropdown-item @click="removeAccount()">Remove Account</b-dropdown-item>
      </b-dropdown>
    </b-row>
    <b-row>
      <TransactionList :transactions="account.transactions" class="w-100" />
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

  async removeAccount() {
    const res = await this.dataApi.accountApi.deleteAccount(this.id);
    if (res?.success) {
      this.$router.push("/wallet");
      this.showMessage({ message: "Account removed successfully" });
    } else {
      this.showMessage({ message: "Error occurred. Could not remove account.", variant: "danger" });
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss"></style>
