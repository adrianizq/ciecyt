import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IElementoProyecto } from '@/shared/model/elemento-proyecto.model';

const baseApiUrl = 'api/elemento-proyectos';

export default class ElementoProyectoService {
  public find(id: number): Promise<IElementoProyecto> {
    return new Promise<IElementoProyecto>((resolve, reject) => {
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

  public create(entity: IElementoProyecto): Promise<IElementoProyecto> {
    return new Promise<IElementoProyecto>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: IElementoProyecto): Promise<IElementoProyecto> {
    return new Promise<IElementoProyecto>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  //quitar paginacion
  public retrieveElementoProyecto(idProyecto?: number, idFase?: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('api/elemento-proyecto-proyecto' + `/${idProyecto}` + `/${idFase}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }
}
