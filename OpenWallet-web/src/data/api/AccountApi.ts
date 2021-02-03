import { BaseApi } from "@/data/api/BaseApi";
import { Account } from "../models/Account";
import { Endpoints } from "./Endpoints";

export class AccountApi extends BaseApi {
  async saveAccount(account: Account): Promise<Account> {
    const res = await this.callApi(Endpoints.ACCOUNTS, "PUT", JSON.stringify(account));
    return await res.json();
  }

  async getAllAccounts(): Promise<Account[]> {
    const res = await this.callApi(Endpoints.ACCOUNTS);
    return await res.json();
  }

  async getAccountById(id: number): Promise<Account> {
    const res = await this.callApi(`${Endpoints.ACCOUNTS}/${id}`);
    return await res.json();
  }
}
