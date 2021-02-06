import { BaseApi } from "@/data/api/BaseApi";
import { Transaction } from "../models/Transaction";
import { Endpoints } from "./Endpoints";

export class TransactionApi extends BaseApi {
  async saveTransaction(Transaction: Transaction): Promise<Transaction | null> {
    const res = await this.callApi(Endpoints.TRANSACTIONS, "PUT", JSON.stringify(Transaction));
    if (!res?.ok) {
      return null;
    }
    return await res?.json();
  }

  async getAllTransactions(): Promise<Transaction[] | null> {
    const res = await this.callApi(Endpoints.TRANSACTIONS);
    if (!res?.ok) {
      return null;
    }
    return await res?.json();
  }

  async getTransactionById(id: number): Promise<Transaction | null> {
    const res = await this.callApi(`${Endpoints.TRANSACTIONS}/${id}`);
    if (!res?.ok) {
      return null;
    }
    return await res?.json();
  }
}
