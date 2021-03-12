<template>
  <b-form @submit.prevent="saveAccount">
    <EntitySelect
      id="institution"
      label="Institution"
      :optionsFunction="getInstitutions"
      :optionTextFunction="institutionTextFunction"
      v-model="account.institution"
    />

    <div v-if="isOpenWalletInstitution">
      <LabelledInput id="name" label="Account Name" type="text" v-model="account.name" />
    </div>

    <b-button class="mt-2" variant="success" type="submit">Add Account</b-button>
  </b-form>
</template>

<script lang="ts">
import { Component, Prop } from "vue-property-decorator";
import { BaseComponent } from "@/components/BaseComponent";
import { Account } from "@/data/models/Account";
import LabelledInput from "@/components/util/LabelledInput.vue";
import EntitySelect from "@/components/util/EntitySelect.vue";
import { Institution } from "@/data/models/Institution";
import { SimpleResponse } from "@/data/models/SimpleResponse";

@Component({ components: { LabelledInput, EntitySelect } })
export default class AccountForm extends BaseComponent {
  getInstitutions = this.dataApi.institutionApi.getAllInstitutions.bind(this.dataApi.institutionApi);
  institutionTextFunction = (entity: Institution) => entity.name;

  async saveAccount() {
    let res: Account | SimpleResponse | null = null;
    if (this.isOpenWalletInstitution) {
      res = await this.dataApi.accountApi.saveAccount(this.account);
    } else if (this.account.institution.id) {
      res = await this.dataApi.institutionApi.syncAccountsForInstitution(this.account.institution.id);
      // Check if we need to request consent.
      if (res.redirectUrl) {
        const result = await this.$bvModal.msgBoxConfirm(
          "Consent is required to be able to use your information. Please visit " + res.redirectUrl
        );
        // when we get a result, we want to sync again now we have consent
        console.log("response from dialog is", result);
        if (result) {
          await this.dataApi.institutionApi.syncAccountsForInstitution(this.account.institution.id);
        }
      }
    }

    if (res) {
      this.$emit("success");
    } else {
      this.showMessage({ message: "Please select an institution." });
    }
  }

  @Prop({
    default: () => {
      return { name: "", institution: { id: 0 } };
    }
  })
  value!: Account;

  get account() {
    return this.value;
  }

  set account(account) {
    this.$emit("input", account);
  }

  get isOpenWalletInstitution() {
    return this.isOpenWalletSource(this.account.institution);
  }
}
</script>

<style scoped lang="scss"></style>
