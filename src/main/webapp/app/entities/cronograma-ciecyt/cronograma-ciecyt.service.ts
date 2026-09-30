import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ICronogramaCiecyt } from '@/shared/model/cronograma-ciecyt.model';

const baseApiUrl = 'api/cronograma-ciecyts';

export default class CronogramaCiecytService {
  public find(id: number): Promise<ICronogramaCiecyt> {
    return new Promise<ICronogramaCiecyt>((resolve, reject) => {
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

  public create(entity: ICronogramaCiecyt): Promise<ICronogramaCiecyt> {
    return new Promise<ICronogramaCiecyt>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: ICronogramaCiecyt): Promise<ICronogramaCiecyt> {
    return new Promise<ICronogramaCiecyt>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }
}
