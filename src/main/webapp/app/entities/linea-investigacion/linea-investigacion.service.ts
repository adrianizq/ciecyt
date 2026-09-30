import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ILineaInvestigacion } from '@/shared/model/linea-investigacion.model';

const baseApiUrl = 'api/linea-investigacions';

export default class LineaInvestigacionService {
  public find(id: number): Promise<ILineaInvestigacion> {
    return new Promise<ILineaInvestigacion>((resolve, reject) => {
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

  public create(entity: ILineaInvestigacion): Promise<ILineaInvestigacion> {
    return new Promise<ILineaInvestigacion>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: ILineaInvestigacion): Promise<ILineaInvestigacion> {
    return new Promise<ILineaInvestigacion>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }
}
