import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IPrograma } from '@/shared/model/programa.model';

const baseApiUrl = 'api/programas';

export default class ProgramaService {
  public find(id: number): Promise<IPrograma> {
    return new Promise<IPrograma>(resolve => {
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

  public create(entity: IPrograma): Promise<IPrograma> {
    return new Promise<IPrograma>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IPrograma): Promise<IPrograma> {
    return new Promise<IPrograma>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public findByCiclo(ciclo: string): Promise<IPrograma[]> {
    return new Promise<IPrograma[]>(resolve => {
      axios.get(`${baseApiUrl}/ciclo/${encodeURIComponent(ciclo)}`).then(function (res) {
        resolve(res.data);
      });
    });
  }
}
