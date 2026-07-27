import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IDepartamento } from '@/shared/model/departamento.model';

const baseApiUrl = 'api/departamentos';

export default class DepartamentoService {
  public find(id: number): Promise<IDepartamento> {
    return new Promise<IDepartamento>(resolve => {
      axios.get(`${baseApiUrl}/${id}`).then(function (res) {
        resolve(res.data);
      });
    });
  }

  /*public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function (res) {
        resolve(res);
      });
    });
  }*/

  public retrieve(): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(baseApiUrl).then(function (res) {
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

  public create(entity: IDepartamento): Promise<IDepartamento> {
    return new Promise<IDepartamento>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IDepartamento): Promise<IDepartamento> {
    return new Promise<IDepartamento>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }
}
