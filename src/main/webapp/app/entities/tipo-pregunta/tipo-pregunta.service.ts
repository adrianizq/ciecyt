import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ITipoPregunta } from '@/shared/model/tipo-pregunta.model';

const baseApiUrl = 'api/tipo-preguntas';

export default class TipoPreguntaService {
  public find(id: number): Promise<ITipoPregunta> {
    return new Promise<ITipoPregunta>(resolve => {
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

  public create(entity: ITipoPregunta): Promise<ITipoPregunta> {
    return new Promise<ITipoPregunta>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ITipoPregunta): Promise<ITipoPregunta> {
    return new Promise<ITipoPregunta>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }
}
