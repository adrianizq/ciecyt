import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IProductoProyecto } from '@/shared/model/producto-proyecto.model';

const baseApiUrl = 'api/producto-proyectos';

export default class ProductoProyectoService {
  public find(id: number): Promise<IProductoProyecto> {
    return new Promise<IProductoProyecto>((resolve, reject) => {
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

  public create(entity: IProductoProyecto): Promise<IProductoProyecto> {
    return new Promise<IProductoProyecto>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: IProductoProyecto): Promise<IProductoProyecto> {
    return new Promise<IProductoProyecto>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }
}
