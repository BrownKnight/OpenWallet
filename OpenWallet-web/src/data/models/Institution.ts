import { BaseEntity } from "./BaseEntity";

export type Institution = {
  name: string;
  logoUrl?: string;
  iconUrl?: string;
} & BaseEntity;
