import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IInvestigacionTipo } from '@/shared/model/investigacion-tipo.model';

const baseApiUrl = 'api/investigacion-tipos';

export default class InvestigacionTipoService {
  public find(id: number): Promise<IInvestigacionTipo> {
    return new Promise<IInvestigacionTipo>((resolve, reject) => {
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

  public create(entity: IInvestigacionTipo): Promise<IInvestigacionTipo> {
    return new Promise<IInvestigacionTipo>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: IInvestigacionTipo): Promise<IInvestigacionTipo> {
    return new Promise<IInvestigacionTipo>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }
}
