import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IUsuario } from '@/shared/model/usuario.model';

const baseApiUrl = 'api/usuarios';

export default class UsuarioService {
  public find(id: number): Promise<IUsuario> {
    return new Promise<IUsuario>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(function (err) {
          reject(err);
        });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }

  public create(entity: IUsuario): Promise<IUsuario> {
    return new Promise<IUsuario>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: IUsuario): Promise<IUsuario> {
    return new Promise<IUsuario>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  /*
  public retrieveAsesores(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios.get(`api/users/asesores` + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
        resolve(res);
      }).catch(reject);
    });
  } */

  public retrieveAsesores(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`api/users/asesores`)
        .then(function (res) {
          resolve(res);
        })
        .catch(function (err) {
          reject(err);
        });
    });
  }

  public retrieveEstudiantes(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`api/users/estudiantes`)
        .then(function (res) {
          resolve(res);
        })
        .catch(function (err) {
          reject(err);
        });
    });
  }

  public retrieveJurados(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`api/users/jurados`)
        .then(function (res) {
          resolve(res);
        })
        .catch(function (err) {
          reject(err);
        });
    });
  }
}
