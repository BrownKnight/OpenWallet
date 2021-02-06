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

  generateTestTransactions(number: number, accountId: number): Transaction[] {
    const transactions: Transaction[] = [];
    for (let i = 0; i < number; i++) {
      transactions.push(this.generateTestTransaction(new Date(), accountId));
    }

    return transactions;
  }

  generateTestTransaction(date: Date, accountId: number): Transaction {
    return {
      account: { id: accountId },
      amount: Math.random() * 100,
      description: `RANDOM ${Math.random() * 100}`,
      transactionDate: date
    };
  }
}
