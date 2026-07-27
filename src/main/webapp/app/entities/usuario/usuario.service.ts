import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IUsuario } from '@/shared/model/usuario.model';

const baseApiUrl = 'api/usuarios';

export default class UsuarioService {
  public find(id: number): Promise<IUsuario> {
    return new Promise<IUsuario>(resolve => {
      axios.get(`${baseApiUrl}/${id}`).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function (res) {
        resolve(res);
      });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>(resolve => {
      axios.delete(`${baseApiUrl}/${id}`).then(function (res) {
        resolve(res);
      });
    });
  }

  public create(entity: IUsuario): Promise<IUsuario> {
    return new Promise<IUsuario>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IUsuario): Promise<IUsuario> {
    return new Promise<IUsuario>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  /*
  public retrieveAsesores(paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`api/users/asesores` + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
        resolve(res);
      });
    });
  } */

  public retrieveAsesores(paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      //axios.get(`api/users/asesores` + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
      //resolve(res);
      axios.get(`api/users/asesores`).then(function (res) {
        resolve(res);
      });
    });
  }

  public retrieveEstudiantes(paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      //axios.get(`api/users/estudiantes` + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
      // resolve(res);
      axios.get(`api/users/estudiantes`).then(function (res) {
        resolve(res);
      });
    });
  }

  public retrieveJurados(paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      //axios.get(`api/users/jurados` + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
      // resolve(res);
      axios.get(`api/users/jurados`).then(function (res) {
        resolve(res);
      });
    });
  }
}
