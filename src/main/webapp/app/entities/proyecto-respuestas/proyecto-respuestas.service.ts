import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IProyectoRespuestas } from '@/shared/model/proyecto-respuestas.model';

const baseApiUrl = 'api/proyecto-respuestas';

export default class ProyectoRespuestasService {
  public find(id: number): Promise<IProyectoRespuestas> {
    return new Promise<IProyectoRespuestas>(resolve => {
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

  public create(entity: IProyectoRespuestas): Promise<IProyectoRespuestas> {
    return new Promise<IProyectoRespuestas>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IProyectoRespuestas): Promise<IProyectoRespuestas> {
    return new Promise<IProyectoRespuestas>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public retrieveProyectoRespuestas(id?: number, faseId?: number, authority?: string): Promise<any> {
    return new Promise<any>(resolve => {
      //axios.get('api/proyecto-respuestas-proyecto' + `/${id}`).then(function (res) {
      axios.get('api/proyecto-respuestas-proyecto-fase-authority' + `/${id}/${faseId}/${authority}`).then(function (res) {
        resolve(res);
      });
    });
  }
}
