import { Store } from "@/authStore";
import { Router } from "@/main";

export class BaseApi {
  showMessage: ({ message, variant, delay }: { message: string; variant?: string; delay?: number }) => void;

  constructor(
    messageFunction: ({ message, variant, delay }: { message: string; variant?: string; delay?: number }) => void
  ) {
    this.showMessage = messageFunction;
  }

  async callApi(url: string, method = "GET", body?: string): Promise<Response> {
    console.log("Calling OW Api");
    return fetch(url, {
      method: method,
      body: body,
      headers: { "Content-Type": "application/json", Authorization: `Bearer ${Store.state.AuthModule.token}` }
    })
      .then(res => {
        if (res.status === 500) {
          throw `Attempted to call ${url} with a token (${Store.state.AuthModule.token}) that wasn't accepted, status ${res.status}`;
        }

        if (res.status === 401) {
          Store.commit("invalidateToken");
          Router.push("/login");
          throw `Attempted to call ${url} with a token (${Store.state.AuthModule.token}) that wasn't accepted, status ${res.status}`;
        }

        return res;
      })
      .catch(error => {
        console.error(error);
        this.showMessage({ message: "Unknown error occurred. Session Ended", variant: "danger" });
        return Promise.reject();
      });
  }
}
