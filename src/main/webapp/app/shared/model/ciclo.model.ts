export interface ICiclo {
  id?: number;
  ciclo?: string;
}

export class Ciclo implements ICiclo {
  constructor(
    public id?: number,
    public ciclo?: string
  ) {}
}
