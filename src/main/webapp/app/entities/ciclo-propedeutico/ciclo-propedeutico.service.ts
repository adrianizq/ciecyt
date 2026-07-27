import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ICicloPropedeutico } from '@/shared/model/ciclo-propedeutico.model';

const baseApiUrl = 'api/ciclo-propedeuticos';

export default class CicloPropedeuticoService {
  public find(id: number): Promise<ICicloPropedeutico> {
    return new Promise<ICicloPropedeutico>(resolve => {
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

  public create(entity: ICicloPropedeutico): Promise<ICicloPropedeutico> {
    return new Promise<ICicloPropedeutico>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ICicloPropedeutico): Promise<ICicloPropedeutico> {
    return new Promise<ICicloPropedeutico>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }
}
