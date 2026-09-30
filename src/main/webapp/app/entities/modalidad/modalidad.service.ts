import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IModalidad } from '@/shared/model/modalidad.model';

const baseApiUrl = 'api/modalidads';

export default class ModalidadService {
  public find(id: number): Promise<IModalidad> {
    return new Promise<IModalidad>((resolve, reject) => {
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

  public create(entity: IModalidad): Promise<IModalidad> {
    return new Promise<IModalidad>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: IModalidad): Promise<IModalidad> {
    return new Promise<IModalidad>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public retrieveModalidadPregunta(idPregunta?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('/api/modalidad-pregunta' + `/${idPregunta}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }
}
