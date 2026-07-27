export interface IPreguntaAuthority {
  id?: number;
  pregunta3Id?: number;
  authorityName?: string;
}

export class PreguntaAuthority implements IPreguntaAuthority {
  constructor(public id?: number, public pregunta3Id?: number, public authorityName?: string) {}
}
