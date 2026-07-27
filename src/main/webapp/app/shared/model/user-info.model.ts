export interface IUserInfo {
  id?: number;
  nuip?: string;
  codigoItp?: string;
  genero?: string;
  celular?: string;
  telefono?: string;
  foto?: any;
  fotoContentType?: string;
  userInfoUserId?: number;
}

export class UserInfo implements IUserInfo {
  constructor(
    public id?: number,
    public nuip?: string,
    public codigoItp?: string,
    public genero?: string,
    public celular?: string,
    public telefono?: string,
    public foto?: any,
    public fotoContentType?: string,
    public userInfoUserId?: number
  ) {}
}
