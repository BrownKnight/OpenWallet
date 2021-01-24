import { BaseApi } from "@/data/api/BaseApi";
import { Institution } from "../models/Institution";
import { Endpoints } from "./Endpoints";

export class InstitutionApi extends BaseApi {
  async getAllInstitutions(): Promise<Institution> {
    const res = await this.callApi(Endpoints.INSTITUTIONS);
    return await res.json();
  }
}
