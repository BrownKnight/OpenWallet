import { Store } from "@/authStore";
import { BaseApi } from "@/data/api/BaseApi";
import { Router } from "@/main";
import { AuthRequest } from "../models/AuthRequest";
import { UserRegistrationRequest } from "../models/UserRegistration";
import { Endpoints } from "./Endpoints";

export class UserApi extends BaseApi {
  login(authRequest: AuthRequest) {
    this.callApi(Endpoints.LOGIN, "POST", JSON.stringify(authRequest))
      .then(res => {
        if (res?.status === 401) {
          throw "Incorrect username/password";
        } else {
          return res?.json();
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

  async register(userRegistrationRequest: UserRegistrationRequest): Promise<boolean> {
    const res = await this.callApi(Endpoints.REGISTER, "POST", JSON.stringify(userRegistrationRequest));
    if (res?.status === 409) {
      this.showMessage({ message: "Account already exists!", variant: "danger" });
      return false;
    } else {
      return true;
    }
  }
}
