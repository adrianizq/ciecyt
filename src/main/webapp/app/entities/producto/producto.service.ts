import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IProducto } from '@/shared/model/producto.model';

const baseApiUrl = 'api/productos';

export default class ProductoService {
  public find(id: number): Promise<IProducto> {
    return new Promise<IProducto>(resolve => {
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

  public create(entity: IProducto): Promise<IProducto> {
    return new Promise<IProducto>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IProducto): Promise<IProducto> {
    return new Promise<IProducto>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }
}
