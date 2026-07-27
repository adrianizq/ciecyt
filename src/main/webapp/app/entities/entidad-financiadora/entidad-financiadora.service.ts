import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IEntidadFinanciadora } from '@/shared/model/entidad-financiadora.model';

const baseApiUrl = 'api/entidad-financiadoras';

export default class EntidadFinanciadoraService {
  public find(id: number): Promise<IEntidadFinanciadora> {
    return new Promise<IEntidadFinanciadora>(resolve => {
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

  public create(entity: IEntidadFinanciadora): Promise<IEntidadFinanciadora> {
    return new Promise<IEntidadFinanciadora>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IEntidadFinanciadora): Promise<IEntidadFinanciadora> {
    return new Promise<IEntidadFinanciadora>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public retrieveEntidadFinanciadora(id?: number, paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get('api/entidad-financiadora-proyecto' + `/${id}` + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function (res) {
        resolve(res);
      });
    });
  }
}
