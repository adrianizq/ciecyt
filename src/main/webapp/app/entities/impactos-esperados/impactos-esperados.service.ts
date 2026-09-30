import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IImpactosEsperados } from '@/shared/model/impactos-esperados.model';

const baseApiUrl = 'api/impactos-esperados';

export default class ImpactosEsperadosService {
  public find(id: number): Promise<IImpactosEsperados> {
    return new Promise<IImpactosEsperados>((resolve, reject) => {
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

  public create(entity: IImpactosEsperados): Promise<IImpactosEsperados> {
    return new Promise<IImpactosEsperados>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: IImpactosEsperados): Promise<IImpactosEsperados> {
    return new Promise<IImpactosEsperados>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public retrieveImpactosEsperados(id?: number, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('api/impactos-esperados-proyecto' + `/${id}` + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }
}
