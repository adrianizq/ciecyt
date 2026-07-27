import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ISolicitud } from '@/shared/model/solicitud.model';

const baseApiUrl = 'api/solicituds';

export default class SolicitudService {
  public find(id: number): Promise<ISolicitud> {
    return new Promise<ISolicitud>(resolve => {
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

  public create(entity: ISolicitud): Promise<ISolicitud> {
    return new Promise<ISolicitud>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ISolicitud): Promise<ISolicitud> {
    return new Promise<ISolicitud>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }
}
