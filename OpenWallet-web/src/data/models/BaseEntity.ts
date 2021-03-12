import { DataSource } from "./DataSource";

export type BaseEntity = {
  id: number;
  dateModified: Date;
  externalId: string;
  dataSource: DataSource;
};
