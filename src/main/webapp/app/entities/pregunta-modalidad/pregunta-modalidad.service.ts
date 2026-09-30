import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IPreguntaModalidad } from '@/shared/model/pregunta-modalidad.model';

const baseApiUrl = 'api/pregunta-modalidads';

export default class PreguntaModalidadService {
  public find(id: number): Promise<IPreguntaModalidad> {
    return new Promise<IPreguntaModalidad>((resolve, reject) => {
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

  public create(entity: IPreguntaModalidad): Promise<IPreguntaModalidad> {
    return new Promise<IPreguntaModalidad>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: IPreguntaModalidad): Promise<IPreguntaModalidad> {
    return new Promise<IPreguntaModalidad>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public retrievePreguntasModalidad(idModalidad: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('api/pregunta-modalidad' + `/${idModalidad}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }

  public retrievePreguntaModalidadIdPregunta(idPregunta: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('api/pregunta-modalidad-preguntaid' + `/${idPregunta}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }

  ///api/pregunta-modalidad-preguntaid/

  /*public retrievePreguntasModalidadyFase(id: number, idFase: number, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('api/pregunta-modalidad-fase' + `/${id}` + `/${idFase}` + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(function (res) {
          resolve(res);
        }).catch(reject);
    });
  }*/
}
