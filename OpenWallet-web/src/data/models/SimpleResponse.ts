type SimpleResponseKnown = {
  success: boolean;
  message: string;
};

export type SimpleResponse = SimpleResponseKnown & Record<string, unknown>;
