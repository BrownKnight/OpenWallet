import { UserApi } from "./api/UserApi";
import { InstitutionApi } from "./api/InstitutionApi";
import { AccountApi } from "./api/AccountApi";
import { TransactionApi } from "./api/TransactionApi";

export class DataApi {
  userApi: UserApi;
  institutionApi: InstitutionApi;
  accountApi: AccountApi;
  transactionApi: TransactionApi;

  constructor(
    messageFunction: ({ message, variant, delay }: { message: string; variant?: string; delay?: number }) => void
  ) {
    this.userApi = new UserApi(messageFunction);
    this.institutionApi = new InstitutionApi(messageFunction);
    this.accountApi = new AccountApi(messageFunction);
    this.transactionApi = new TransactionApi(messageFunction);
  }
}
