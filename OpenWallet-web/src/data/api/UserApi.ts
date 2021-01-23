import { Store } from "@/authStore";
import { BaseApi } from "@/data/api/BaseApi";
import { Endpoints } from "./Endpoints";

export class UserApi extends BaseApi {
  login(username: string, password: string) {
    this.callApi(Endpoints.LOGIN, "POST", JSON.stringify({ username: username, password: password })).then(json => {
      if (json.token) {
        Store.commit("setToken", json.token);
      }
    });
  }
}
