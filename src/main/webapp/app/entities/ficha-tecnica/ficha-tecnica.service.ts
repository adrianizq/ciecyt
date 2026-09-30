import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IFichaTecnica } from '@/shared/model/ficha-tecnica.model';

const baseApiUrl = 'api/ficha-tecnicas';

export default class FichaTecnicaService {
  public find(id: number): Promise<IFichaTecnica> {
    return new Promise<IFichaTecnica>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
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

  public create(entity: IFichaTecnica): Promise<IFichaTecnica> {
    return new Promise<IFichaTecnica>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: IFichaTecnica): Promise<IFichaTecnica> {
    return new Promise<IFichaTecnica>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }
}
