import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IFormato } from '@/shared/model/formato.model';

const baseApiUrl = 'api/formatoes';

export default class FormatoService {
  public find(id: number): Promise<IFormato> {
    return new Promise<IFormato>(resolve => {
      axios.get(`${baseApiUrl}/${id}`).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public findByCodigo(codigo: string): Promise<IFormato> {
    return new Promise<IFormato>(resolve => {
      axios.get(`api/formato/${codigo}`).then(function (res) {
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

  public create(entity: IFormato): Promise<IFormato> {
    return new Promise<IFormato>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IFormato): Promise<IFormato> {
    return new Promise<IFormato>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }
}
