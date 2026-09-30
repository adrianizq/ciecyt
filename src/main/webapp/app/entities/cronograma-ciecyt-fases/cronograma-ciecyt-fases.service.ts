import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ICronogramaCiecytFases } from '@/shared/model/cronograma-ciecyt-fases.model';

const baseApiUrl = 'api/cronograma-ciecyt-fases';

export default class CronogramaCiecytFasesService {
  public find(id: number): Promise<ICronogramaCiecytFases> {
    return new Promise<ICronogramaCiecytFases>((resolve, reject) => {
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

  public create(entity: ICronogramaCiecytFases): Promise<ICronogramaCiecytFases> {
    return new Promise<ICronogramaCiecytFases>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: ICronogramaCiecytFases): Promise<ICronogramaCiecytFases> {
    return new Promise<ICronogramaCiecytFases>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }
}
