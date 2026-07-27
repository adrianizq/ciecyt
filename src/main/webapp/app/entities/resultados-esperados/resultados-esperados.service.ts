import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IResultadosEsperados } from '@/shared/model/resultados-esperados.model';

const baseApiUrl = 'api/resultados-esperados';

export default class ResultadosEsperadosService {
  public find(id: number): Promise<IResultadosEsperados> {
    return new Promise<IResultadosEsperados>(resolve => {
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

  public create(entity: IResultadosEsperados): Promise<IResultadosEsperados> {
    return new Promise<IResultadosEsperados>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IResultadosEsperados): Promise<IResultadosEsperados> {
    return new Promise<IResultadosEsperados>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public retrieveResultadosEsperados(id?: number, paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get('api/resultados-esperados-proyecto' + `/${id}` + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function (res) {
        resolve(res);
      });
    });
  }
}
