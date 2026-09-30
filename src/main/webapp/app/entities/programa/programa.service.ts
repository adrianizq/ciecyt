import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IPrograma } from '@/shared/model/programa.model';

const baseApiUrl = 'api/programas';

export default class ProgramaService {
  public find(id: number): Promise<IPrograma> {
    return new Promise<IPrograma>((resolve, reject) => {
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

  public create(entity: IPrograma): Promise<IPrograma> {
    return new Promise<IPrograma>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: IPrograma): Promise<IPrograma> {
    return new Promise<IPrograma>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public findByCiclo(ciclo: string): Promise<IPrograma[]> {
    return new Promise<IPrograma[]>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/ciclo/${encodeURIComponent(ciclo)}`)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }
}
