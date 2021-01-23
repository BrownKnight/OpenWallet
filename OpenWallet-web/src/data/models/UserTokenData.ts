export class UserTokenData {
  id: string;
  username: string;

  constructor(tokenSubject: string) {
    const parts = tokenSubject.split(",");
    this.id = parts[0];
    this.username = parts[1];
  }
}