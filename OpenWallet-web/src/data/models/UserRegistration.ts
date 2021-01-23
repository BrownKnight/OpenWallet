export type UserRegistration = {
  emailAddress: string;
  password: string;
  firstName: string;
  lastName: string;
};

export class UserRegistrationRequest {
  username: string;
  password: string;
  user: {
    firstName: string;
    lastName: string;
    emailAddress: string;
  };

  constructor(reg: UserRegistration) {
    this.user = {
      firstName: reg.firstName,
      lastName: reg.lastName,
      emailAddress: reg.emailAddress
    };
    this.username = reg.emailAddress;
    this.password = reg.password;
  }
}
