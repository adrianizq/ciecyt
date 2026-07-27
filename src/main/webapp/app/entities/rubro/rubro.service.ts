import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IRubro } from '@/shared/model/rubro.model';

const baseApiUrl = 'api/rubros';

export default class RubroService {
  public find(id: number): Promise<IRubro> {
    return new Promise<IRubro>(resolve => {
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

  public create(entity: IRubro): Promise<IRubro> {
    return new Promise<IRubro>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IRubro): Promise<IRubro> {
    return new Promise<IRubro>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }
}
