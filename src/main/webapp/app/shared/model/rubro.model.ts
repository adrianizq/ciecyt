export interface IRubro {
  id?: number;
  rubro?: string;
}

export class Rubro implements IRubro {
  constructor(public id?: number, public rubro?: string) {}
}
