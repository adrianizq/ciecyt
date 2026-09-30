import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ITipoPregunta } from '@/shared/model/tipo-pregunta.model';

const baseApiUrl = 'api/tipo-preguntas';

export default class TipoPreguntaService {
  public find(id: number): Promise<ITipoPregunta> {
    return new Promise<ITipoPregunta>((resolve, reject) => {
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

  public create(entity: ITipoPregunta): Promise<ITipoPregunta> {
    return new Promise<ITipoPregunta>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: ITipoPregunta): Promise<ITipoPregunta> {
    return new Promise<ITipoPregunta>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }
}
