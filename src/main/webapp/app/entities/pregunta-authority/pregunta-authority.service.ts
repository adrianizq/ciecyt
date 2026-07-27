import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IPreguntaAuthority } from '@/shared/model/pregunta-authority.model';

const baseApiUrl = 'api/pregunta-modalidads';

export default class IPreguntaAuthorityService {
  public find(id: number): Promise<IPreguntaAuthority> {
    return new Promise<IPreguntaAuthority>(resolve => {
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

  public create(entity: IPreguntaAuthority): Promise<IPreguntaAuthority> {
    return new Promise<IPreguntaAuthority>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IPreguntaAuthority): Promise<IPreguntaAuthority> {
    return new Promise<IPreguntaAuthority>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public retrievePreguntasAuthority(idPregunta: number): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get('api/pregunta-authority' + `/${idPregunta}`).then(function (res) {
        resolve(res);
      });
    });
  }

  public retrievePreguntaAuthorityIdPregunta(idPregunta: number): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get('api/pregunta-authority-preguntaid' + `/${idPregunta}`).then(function (res) {
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
