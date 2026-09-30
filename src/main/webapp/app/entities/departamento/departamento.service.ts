import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IDepartamento } from '@/shared/model/departamento.model';

const baseApiUrl = 'api/departamentos';

export default class DepartamentoService {
  public find(id: number): Promise<IDepartamento> {
    return new Promise<IDepartamento>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  /*public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios.get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function (res) {
        resolve(res);
      }).catch(reject);
    });
  }*/

  public retrieve(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
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

  public create(entity: IDepartamento): Promise<IDepartamento> {
    return new Promise<IDepartamento>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: IDepartamento): Promise<IDepartamento> {
    return new Promise<IDepartamento>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }
}
