<template>
  <footer class="footer bg-light mt-5 container-fluid">
    <b-modal id="issue-modal" title="Report an issue" :aria-busy="true" hide-footer>
      <p>
        Found an issue? Please report it <a href="https://github.com/BrownKnight/STRUDEL/issues">here on GitHub</a>, so
        I can <em>professionally</em> ignore it.
      </p>
    </b-modal>

    <b-row class="px-0 justify-content-between align-items-center mx-1 mx-md-5">
      <b-button size="sm" variant="outline-primary" href="/api/docs">
        <b-icon icon="newspaper" class="align-top mr-1"></b-icon>
        API Docs
      </b-button>
      <b-button size="sm" variant="outline-primary" v-b-modal.issue-modal>
        <b-icon icon="bug" class="align-top mr-1"></b-icon>
        Found an issue?
      </b-button>
      <b-link :href="`https://github.com/BrownKnight/OpenWallet/releases/tag/${versionNumber}`">
        <small>{{ versionNumber }}</small>
      </b-link>
    </b-row>
  </footer>
</template>

<script lang="ts">
import { Component } from "vue-property-decorator";
import { BaseComponent } from "./BaseComponent";

@Component
export default class Footer extends BaseComponent {
  get versionNumber() {
    return process.env.VUE_APP_GITHUB_REF?.replace("refs/tags/", "") ?? "Unknown";
  }
}
</script>

<style scoped lang="scss">
.footer {
  height: 4em;
  line-height: 4em;
}
</style>
