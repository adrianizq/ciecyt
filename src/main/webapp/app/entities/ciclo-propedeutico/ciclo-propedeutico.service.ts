import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ICicloPropedeutico } from '@/shared/model/ciclo-propedeutico.model';

const baseApiUrl = 'api/ciclo-propedeuticos';

export default class CicloPropedeuticoService {
  public find(id: number): Promise<ICicloPropedeutico> {
    return new Promise<ICicloPropedeutico>((resolve, reject) => {
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

  public create(entity: ICicloPropedeutico): Promise<ICicloPropedeutico> {
    return new Promise<ICicloPropedeutico>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: ICicloPropedeutico): Promise<ICicloPropedeutico> {
    return new Promise<ICicloPropedeutico>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }
}
