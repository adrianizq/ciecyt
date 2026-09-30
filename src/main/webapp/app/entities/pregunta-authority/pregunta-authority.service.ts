import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IPreguntaAuthority } from '@/shared/model/pregunta-authority.model';

const baseApiUrl = 'api/pregunta-modalidads';

export default class IPreguntaAuthorityService {
  public find(id: number): Promise<IPreguntaAuthority> {
    return new Promise<IPreguntaAuthority>((resolve, reject) => {
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

  public create(entity: IPreguntaAuthority): Promise<IPreguntaAuthority> {
    return new Promise<IPreguntaAuthority>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: IPreguntaAuthority): Promise<IPreguntaAuthority> {
    return new Promise<IPreguntaAuthority>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public retrievePreguntasAuthority(idPregunta: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('api/pregunta-authority' + `/${idPregunta}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }

  public retrievePreguntaAuthorityIdPregunta(idPregunta: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('api/pregunta-authority-preguntaid' + `/${idPregunta}`)
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
