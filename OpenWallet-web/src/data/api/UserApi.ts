import { Store } from "@/authStore";
import { BaseApi } from "@/data/api/BaseApi";
import { Router } from "@/main";
import { Endpoints } from "./Endpoints";

export class UserApi extends BaseApi {
  login(username: string, password: string) {
    this.callApi(Endpoints.LOGIN, "POST", JSON.stringify({ username: username, password: password }))
      .then(res => {
        if (res.status === 401) {
          throw "Incorrect username/password";
        } else {
          return res.json();
        }
      })
      .then(json => {
        const storedRedirectPath = sessionStorage.getItem("redirectTo");

        if (json.token) {
          Store.commit("setToken", json.token);
          Router.push(storedRedirectPath ?? "/");
        } else {
          throw "Invalid response received";
        }
      })
      .catch(reason => this.showMessage({ message: reason, variant: "danger" }));
  }
}
