import { BaseApi } from "@/data/api/BaseApi";
import { Account } from "../models/Account";
import { Endpoints } from "./Endpoints";

export class AccountApi extends BaseApi {
  async addAccount(newAccount: Account): Promise<Account> {
    const res = await this.callApi(Endpoints.ACCOUNTS, "PUT", JSON.stringify(newAccount));
    return await res.json();
  }

  async getAllAccounts(): Promise<Account[]> {
    const res = await this.callApi(Endpoints.ACCOUNTS);
    return await res.json();
  }
}
