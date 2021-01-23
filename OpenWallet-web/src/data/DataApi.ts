import { UserApi } from "./api/UserApi";

export class DataApi {
  userApi: UserApi;

  constructor(
    messageFunction: ({ message, variant, delay }: { message: string; variant?: string; delay?: number }) => void
  ) {
    this.userApi = new UserApi(messageFunction);
  }
}
