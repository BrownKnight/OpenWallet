import Vue from "vue";
import Vuex from "vuex";
import { Module, VuexModule, Mutation } from "vuex-module-decorators";
import jwt from "jsonwebtoken";
import createPersistedState from "vuex-persistedstate";
import { UserTokenData } from "./data/models/UserTokenData";

@Module
class AuthModule extends VuexModule {
  token: string | null = null;
  userData: UserTokenData | null = null;

  @Mutation
  setToken(token: string) {
    this.token = token;
    // all tokens sent to the server are validated, so whilst we should be verifying here, it's not a massive security hole
    const tokenSubject = jwt.decode(token)?.sub as string;
    if (tokenSubject) {
      const [userId, username] = tokenSubject.split(",");
      this.userData = { id: userId, username: username };
    }
  }

  @Mutation
  invalidateToken() {
    this.token = null;
    this.userData = null;
  }
}

Vue.use(Vuex);

export const Store = new Vuex.Store({
  state: {},
  modules: {
    AuthModule
  },
  plugins: [createPersistedState()]
});
