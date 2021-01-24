import { UserApi } from "./api/UserApi";
import { InstitutionApi } from "./api/InstitutionApi";
import { AccountApi } from "./api/AccountApi";

export class DataApi {
  userApi: UserApi;
  institutionApi: InstitutionApi;
  accountApi: AccountApi;

  constructor(
    messageFunction: ({ message, variant, delay }: { message: string; variant?: string; delay?: number }) => void
  ) {
    this.userApi = new UserApi(messageFunction);
    this.institutionApi = new InstitutionApi(messageFunction);
    this.accountApi = new AccountApi(messageFunction);
  }
}
