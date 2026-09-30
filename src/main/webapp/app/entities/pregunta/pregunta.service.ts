import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IPregunta } from '@/shared/model/pregunta.model';

const baseApiUrl = 'api/preguntas';

export default class PreguntaService {
  public find(id: number): Promise<IPregunta> {
    return new Promise<IPregunta>((resolve, reject) => {
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

  public create(entity: IPregunta): Promise<IPregunta> {
    return new Promise<IPregunta>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: IPregunta): Promise<IPregunta> {
    return new Promise<IPregunta>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public retrievePreguntasModalidad(id: number, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('api/pregunta-modalidad' + `/${id}` + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }

  public retrievePreguntasModalidadyFase(id: number, idFase: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('api/pregunta-modalidad-fase' + `/${id}` + `/${idFase}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }

  public retrievePreguntasModalidadyFaseyAuthority(id: number, idFase: number, authority: string): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('api/pregunta-modalidad-fase-authority' + `/${id}` + `/${idFase}` + `/${authority}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }

  public retrieveSearchFase(idFase: any, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${idFase}/searchfase` + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
