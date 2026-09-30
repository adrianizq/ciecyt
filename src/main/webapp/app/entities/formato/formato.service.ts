import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IFormato } from '@/shared/model/formato.model';

const baseApiUrl = 'api/formatoes';

export default class FormatoService {
  public find(id: number): Promise<IFormato> {
    return new Promise<IFormato>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public findByCodigo(codigo: string): Promise<IFormato> {
    return new Promise<IFormato>((resolve, reject) => {
      axios
        .get(`api/formato/${codigo}`)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }

  public create(entity: IFormato): Promise<IFormato> {
    return new Promise<IFormato>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: IFormato): Promise<IFormato> {
    return new Promise<IFormato>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }
}
