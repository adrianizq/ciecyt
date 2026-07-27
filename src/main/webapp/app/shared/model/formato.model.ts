export interface IFormato {
  id?: number;
  formato?: string;
  version?: string;
  codigo?: string;
  fecha?: Date;
}

export class Formato implements IFormato {
  constructor(public id?: number, public formato?: string, public version?: string, public codigo?: string, public fecha?: Date) {}
}
