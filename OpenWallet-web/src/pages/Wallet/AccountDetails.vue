<template>
  <b-container>
    <b-row class="justify-content-between mt-4">
      <h3 class="text-left d-inline-block m-0">{{ account.name }}</h3>
    </b-row>

    <b-row>
      <AccountCard :account="account" />
    </b-row>
  </b-container>
</template>

<script lang="ts">
import { BaseComponent } from "@/components/BaseComponent";
import { Component, Prop } from "vue-property-decorator";
import AccountCard from "@/components/AccountCard.vue";
import { Account } from "@/data/models/Account";

@Component({ components: { AccountCard } })
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
