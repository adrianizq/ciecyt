export interface MenuChildren {
  id?: number;
  nombre?: string;
  url?: string;
  icono?: string;
  activo?: boolean;
  esPublico?: boolean;
  orden?: number;
  menuPadreId?: any;
}

export interface MenuBar {
  id?: number;
  nombre?: string;
  url?: string;
  icono?: string;
  activo?: boolean;
  esPublico?: boolean;
  orden?: number;
  menuPadreId?: any;
  children?: MenuChildren[];
}

export interface IMenu {
  id?: number;
  nombre?: string;
  url?: string;
  icono?: string;
  activo?: boolean;
  esPublico?: boolean;
  menuPadreNombre?: string;
  menuPadreId?: number;
  orden?: number;
}

export class Menu implements IMenu {
  constructor(
    public id?: number,
    public nombre?: string,
    public url?: string,
    public icono?: string,
    public activo?: boolean,
    public esPublico?: boolean,
    public menuPadreNombre?: string,
    public menuPadreId?: number,
    public orden?: number
  ) {
    this.activo = this.activo || true;
    this.esPublico = this.esPublico || false;
  }
}
