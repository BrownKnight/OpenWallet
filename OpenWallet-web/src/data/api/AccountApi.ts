import { BaseApi } from "@/data/api/BaseApi";
import { Account } from "../models/Account";
import { Transaction } from "../models/Transaction";
import { Endpoints } from "./Endpoints";

export class AccountApi extends BaseApi {
  async saveAccount(account: Account): Promise<Account | null> {
    const res = await this.callApi(Endpoints.ACCOUNTS, "PUT", JSON.stringify(account));
    if (!res?.ok) {
      return null;
    }
    return await res?.json();
  }

  async getAllAccounts(): Promise<Account[] | null> {
    const res = await this.callApi(Endpoints.ACCOUNTS);
    if (!res?.ok) {
      return null;
    }
    return await res?.json();
  }

  async getAccountById(id: number): Promise<Account | null> {
    const res = await this.callApi(`${Endpoints.ACCOUNTS}/${id}`);
    if (!res?.ok) {
      return null;
    }
    return await res?.json();
  }

  async addTransactions(accountId: number, transactions: Transaction[]): Promise<Transaction[] | null> {
    const res = await this.callApi(
      `${Endpoints.ACCOUNTS}/${accountId}/transactions`,
      "PUT",
      JSON.stringify(transactions)
    );

    if (!res?.ok) {
      return null;
    }

    return await res?.json();
  }
}
