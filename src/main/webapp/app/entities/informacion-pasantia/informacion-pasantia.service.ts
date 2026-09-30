import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IInformacionPasantia } from '@/shared/model/informacion-pasantia.model';

const baseApiUrl = 'api/informacion-pasantias';

export default class InformacionPasantiaService {
  public find(id: number): Promise<IInformacionPasantia> {
    return new Promise<IInformacionPasantia>((resolve, reject) => {
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

  public create(entity: IInformacionPasantia): Promise<IInformacionPasantia> {
    return new Promise<IInformacionPasantia>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: IInformacionPasantia): Promise<IInformacionPasantia> {
    return new Promise<IInformacionPasantia>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public findInformacionPasantiaProyecto(id: any): Promise<IInformacionPasantia> {
    return new Promise<IInformacionPasantia>((resolve, reject) => {
      axios
        .get('/api/informacion-pasantia-proyecto' + `/${id}`)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }
}
