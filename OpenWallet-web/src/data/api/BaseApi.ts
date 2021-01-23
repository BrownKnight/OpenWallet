import { Store } from "@/authStore";
import { Router } from "@/main";

export class BaseApi {
  showMessage: ({ message, variant, delay }: { message: string; variant?: string; delay?: number }) => void;

  constructor(
    messageFunction: ({ message, variant, delay }: { message: string; variant?: string; delay?: number }) => void
  ) {
    this.showMessage = messageFunction;
  }

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  async callApi(url: string, method = "GET", body?: string): Promise<any> {
    console.log("Calling OW Api");
    return fetch(url, {
      method: method,
      body: body,
      headers: { "Content-Type": "application/json", Authorization: `Bearer ${Store.state.AuthModule.token}` }
    })
      .then(res => {
        if (res.status === 401 || res.status === 500) {
          console.error(
            `Attempted to call ${url} with a token (${Store.state.AuthModule.token}) that wasn't accepted, status ${res.status}`
          );
          Store.commit("invalidateToken");
          Router.push("/login");
          throw "Invalid token";
        }

        return res;
      })
      .then(res => res.json())
      .catch(error => {
        console.error(error);
        this.showMessage({ message: "Unknown error occurred. Session Ended", variant: "danger" });
        return Promise.reject();
      });
  }
}
