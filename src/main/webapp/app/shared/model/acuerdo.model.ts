export interface IAcuerdo {
  id?: number;
  acuerdo?: string;
  version?: string;
  codigo?: string;
  fecha?: Date;
}

export class Acuerdo implements IAcuerdo {
  constructor(public id?: number, public acuerdo?: string, public version?: string, public codigo?: string, public fecha?: Date) {}
}
