import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IModalidad } from '@/shared/model/modalidad.model';

const baseApiUrl = 'api/modalidads';

export default class ModalidadService {
  public find(id: number): Promise<IModalidad> {
    return new Promise<IModalidad>(resolve => {
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

  public create(entity: IModalidad): Promise<IModalidad> {
    return new Promise<IModalidad>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IModalidad): Promise<IModalidad> {
    return new Promise<IModalidad>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public retrieveModalidadPregunta(idPregunta?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get('/api/modalidad-pregunta' + `/${idPregunta}`).then(function (res) {
        resolve(res);
      });
    });
  }
}
