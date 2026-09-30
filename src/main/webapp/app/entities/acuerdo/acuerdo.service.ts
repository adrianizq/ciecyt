import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IAcuerdo } from '@/shared/model/acuerdo.model';

const baseApiUrl = 'api/acuerdos';

export default class AcuerdoService {
  public find(id: number): Promise<IAcuerdo> {
    return new Promise<IAcuerdo>((resolve, reject) => {
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

  public create(entity: IAcuerdo): Promise<IAcuerdo> {
    return new Promise<IAcuerdo>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: IAcuerdo): Promise<IAcuerdo> {
    return new Promise<IAcuerdo>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }
}
