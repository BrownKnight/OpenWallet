import { UserApi } from "./api/UserApi";
import { InstitutionApi } from "./api/InstitutionApi";

export class DataApi {
  userApi: UserApi;
  institutionApi: InstitutionApi;

  constructor(
    messageFunction: ({ message, variant, delay }: { message: string; variant?: string; delay?: number }) => void
  ) {
    this.userApi = new UserApi(messageFunction);
    this.institutionApi = new InstitutionApi(messageFunction);
  }
}
