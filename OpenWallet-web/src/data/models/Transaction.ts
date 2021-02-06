export type Transaction = {
  description: string;
  amount: number;
  transactionDate: Date;
  account: { id: number };
};
