export class Currency {
  public static getSymbol(currencyCode?: string): string {
    switch (currencyCode) {
      case "GBP":
        return "£";
    }

    // Unknown currency
    return "X";
  }
}
