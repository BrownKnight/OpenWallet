import { Account } from "./Account";

export type Transaction = {
  description: string;
  amount: number;
  transactionDate: Date;
  account: Partial<Account>;
};
