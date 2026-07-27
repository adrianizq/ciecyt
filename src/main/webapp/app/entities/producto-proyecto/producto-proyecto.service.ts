import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IProductoProyecto } from '@/shared/model/producto-proyecto.model';

const baseApiUrl = 'api/producto-proyectos';

export default class ProductoProyectoService {
  public find(id: number): Promise<IProductoProyecto> {
    return new Promise<IProductoProyecto>(resolve => {
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

  public create(entity: IProductoProyecto): Promise<IProductoProyecto> {
    return new Promise<IProductoProyecto>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IProductoProyecto): Promise<IProductoProyecto> {
    return new Promise<IProductoProyecto>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }
}
