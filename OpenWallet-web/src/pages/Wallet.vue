<template>
  <b-container>
    <b-row class="justify-content-between mt-4">
      <h3 class="text-left d-inline-block m-0">Wallet</h3>
      <b-button-group>
        <b-button variant="outline-info" size="sm" v-b-modal.add-account-modal>Add Account</b-button>
        <b-button variant="outline-info" size="sm" @click="syncAllAccounts()">Sync</b-button>
      </b-button-group>
    </b-row>

    <b-modal id="add-account-modal" title="Add Account" hide-footer>
      <AccountForm @success="$bvModal.hide('add-account-modal')" />
    </b-modal>

    <b-row class="mt-2">
      <b-col v-for="account in accounts" :key="account.id" cols="12" md="6" class="p-2">
        <AccountCard :account="account" />
      </b-col>
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
    this.accounts = (await this.dataApi.accountApi.getAllAccounts()) ?? [];
  }

  async syncAllAccounts() {
    let res = await this.dataApi.accountApi.synchroniseAllAccounts();
    if (res.success) {
      if (res.redirectUrl) {
        const messageElements = [
          this.$createElement(
            "span",
            "Consent is required for one of your accounts to be able to use your information. Please visit "
          ),
          this.$createElement("a", { attrs: { href: res.redirectUrl } }, "this link to authorise")
        ];
        const result = await this.$bvModal.msgBoxConfirm(messageElements);
        // when we get a ok button click, we want to sync again now we have consent
        if (result) {
          res = await this.dataApi.accountApi.synchroniseAllAccounts();
          if (res.redirectUrl) {
            this.showMessage({
              message: "No consent found for this institution. Please try again.",
              variant: "danger"
            });
          }
        }
      }
      this.showMessage({ message: res.message });
    } else {
      this.showMessage({ message: "Error occurred syncing accounts", variant: "danger" });
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss"></style>
