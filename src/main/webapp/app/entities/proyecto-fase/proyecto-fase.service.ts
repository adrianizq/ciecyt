import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IProyectoFase } from '@/shared/model/proyecto-fase.model';

const baseApiUrl = 'api/proyecto-fases';

export default class ProyectoFaseService {
  public find(id: number): Promise<IProyectoFase> {
    return new Promise<IProyectoFase>((resolve, reject) => {
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

  public create(entity: IProyectoFase): Promise<IProyectoFase> {
    return new Promise<IProyectoFase>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: IProyectoFase): Promise<IProyectoFase> {
    return new Promise<IProyectoFase>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }
}
