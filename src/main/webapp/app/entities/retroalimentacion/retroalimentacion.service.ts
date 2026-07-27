import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IRetroalimentacion } from '@/shared/model/retroalimentacion.model';

const baseApiUrl = 'api/retroalimentacions';

export default class RetroalimentacionService {
  public find(id: number): Promise<IRetroalimentacion> {
    return new Promise<IRetroalimentacion>(resolve => {
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

  public create(entity: IRetroalimentacion): Promise<IRetroalimentacion> {
    return new Promise<IRetroalimentacion>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IRetroalimentacion): Promise<IRetroalimentacion> {
    return new Promise<IRetroalimentacion>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }
}
