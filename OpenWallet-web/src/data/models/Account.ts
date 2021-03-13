import { BaseEntity } from "./BaseEntity";
import { Institution } from "./Institution";

export type Account = {
  name: string;
  institution: Partial<Institution>;
  currency: string;
  balance: number;
} & BaseEntity;
