import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IRequisitoProyecto } from '@/shared/model/requisito-proyecto.model';

const baseApiUrl = 'api/requisito-proyectos';

export default class RequisitoProyectoService {
  public find(id: number): Promise<IRequisitoProyecto> {
    return new Promise<IRequisitoProyecto>((resolve, reject) => {
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

  public findByProyecto(proyectoId: number): Promise<IRequisitoProyecto[]> {
    return new Promise<IRequisitoProyecto[]>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/proyecto/${proyectoId}`)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public generarPorProyecto(proyectoId: number): Promise<IRequisitoProyecto[]> {
    return new Promise<IRequisitoProyecto[]>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/generar/proyecto/${proyectoId}`)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public entregar(id: number, archivo?: string, observacion?: string): Promise<IRequisitoProyecto> {
    return new Promise<IRequisitoProyecto>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/${id}/entregar`, { archivo, observacion })
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public validar(id: number, aprobado: boolean, observacion?: string): Promise<IRequisitoProyecto> {
    return new Promise<IRequisitoProyecto>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/${id}/validar`, { aprobado, observacion })
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public create(entity: IRequisitoProyecto): Promise<IRequisitoProyecto> {
    return new Promise<IRequisitoProyecto>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: IRequisitoProyecto): Promise<IRequisitoProyecto> {
    return new Promise<IRequisitoProyecto>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
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
}
