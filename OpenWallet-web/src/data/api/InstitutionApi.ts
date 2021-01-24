import { BaseApi } from "@/data/api/BaseApi";
import { Account } from "../models/Account";
import { Institution } from "../models/Institution";
import { Endpoints } from "./Endpoints";

export class InstitutionApi extends BaseApi {
  async getAllInstitutions(): Promise<Institution> {
    const res = await this.callApi(Endpoints.INSTITUTIONS);
    return await res.json();
  }

  async addAccount(newAccount: Account): Promise<Account> {
    const res = await this.callApi(Endpoints.INSTITUTIONS, "PUT", JSON.stringify(newAccount));
    return await res.json();
  }
}
