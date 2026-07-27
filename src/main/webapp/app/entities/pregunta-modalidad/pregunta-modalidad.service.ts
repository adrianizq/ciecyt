import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IPreguntaModalidad } from '@/shared/model/pregunta-modalidad.model';

const baseApiUrl = 'api/pregunta-modalidads';

export default class PreguntaModalidadService {
  public find(id: number): Promise<IPreguntaModalidad> {
    return new Promise<IPreguntaModalidad>(resolve => {
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

  public create(entity: IPreguntaModalidad): Promise<IPreguntaModalidad> {
    return new Promise<IPreguntaModalidad>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IPreguntaModalidad): Promise<IPreguntaModalidad> {
    return new Promise<IPreguntaModalidad>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public retrievePreguntasModalidad(idModalidad: number): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get('api/pregunta-modalidad' + `/${idModalidad}`).then(function (res) {
        resolve(res);
      });
    });
  }

  public retrievePreguntaModalidadIdPregunta(idPregunta: number): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get('api/pregunta-modalidad-preguntaid' + `/${idPregunta}`).then(function (res) {
        resolve(res);
      });
    });
  }

  ///api/pregunta-modalidad-preguntaid/

  /*public retrievePreguntasModalidadyFase(id: number, idFase: number, paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios
        .get('api/pregunta-modalidad-fase' + `/${id}` + `/${idFase}` + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(function (res) {
          resolve(res);
        });
    });
  }*/
}
