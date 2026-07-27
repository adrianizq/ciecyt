import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IElementoModalidad } from '@/shared/model/elemento-modalidad.model';

const baseApiUrl = 'api/elemento-modalidads';

export default class ElementoModalidadService {
  public find(id: number): Promise<IElementoModalidad> {
    return new Promise<IElementoModalidad>(resolve => {
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

  public create(entity: IElementoModalidad): Promise<IElementoModalidad> {
    return new Promise<IElementoModalidad>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IElementoModalidad): Promise<IElementoModalidad> {
    return new Promise<IElementoModalidad>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public retrieveElementosModalidad(idModalidad: number): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get('api/elemento-modalidad' + `/${idModalidad}`).then(function (res) {
        resolve(res);
      });
    });
  }
  //trar una lista de elementoModalidad por idElemento*
  public retrieveElementoModalidadIdElemento(idElemento: number): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get('api/elemento-modalidad-elementoid' + `/${idElemento}`).then(function (res) {
        resolve(res);
      });
    });
  }

  public retrieveModalidadElemento(idElemento?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get('/api/elemento-modalidad-elementoid' + `/${idElemento}`).then(function (res) {
        resolve(res);
      });
    });
  }

  ///api/elemento-modalidad-elementoid/

  /*public retrieveElementosModalidadyFase(id: number, idFase: number, paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios
        .get('api/elemento-modalidad-fase' + `/${id}` + `/${idFase}` + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(function (res) {
          resolve(res);
        });
    });
  }*/
}
