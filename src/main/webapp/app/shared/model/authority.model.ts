export interface IAuthority {
  name?: string;
}

export class Authority implements IAuthority {
  constructor(public name?: string) {}
}
