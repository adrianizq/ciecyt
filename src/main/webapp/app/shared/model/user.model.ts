import { IUserInfo, UserInfo } from './user-info.model';

export interface IUser {
  id?: any;
  login?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  activated?: boolean;
  langKey?: string;
  authorities?: any[];
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
  password?: string;
  userInfo?: IUserInfo;

  // nombresApellidos?: string;
}

export class User implements IUser {
  constructor(
    public id?: any,
    public login?: string,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public activated?: boolean,
    public langKey?: string,
    public authorities?: any[],
    public createdBy?: string,
    public createdDate?: Date,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Date,
    public password?: string, //public nombresApellidos?: string,
    public userInfo?: UserInfo
  ) {
    /*this.nombresApellidos=this.firstName + " " + this.lastName*/
  }
  /*public getNombresApellidos(){
      return this.nombresApellidos
  }
  public setNombresApellidos(){
    this.nombresApellidos = this.firstName + " " + this.lastName
  }*/
}
