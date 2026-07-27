import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ICronogramaCiecyt } from '@/shared/model/cronograma-ciecyt.model';

const baseApiUrl = 'api/cronograma-ciecyts';

export default class CronogramaCiecytService {
  public find(id: number): Promise<ICronogramaCiecyt> {
    return new Promise<ICronogramaCiecyt>(resolve => {
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

  public create(entity: ICronogramaCiecyt): Promise<ICronogramaCiecyt> {
    return new Promise<ICronogramaCiecyt>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ICronogramaCiecyt): Promise<ICronogramaCiecyt> {
    return new Promise<ICronogramaCiecyt>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }
}
