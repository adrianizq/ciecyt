export interface ITipoPregunta {
  id?: number;
  tipoPregunta?: string;
  tipoDato?: string;
}

export class TipoPregunta implements ITipoPregunta {
  constructor(public id?: number, public tipoPregunta?: string, public tipoDato?: string) {}
}
