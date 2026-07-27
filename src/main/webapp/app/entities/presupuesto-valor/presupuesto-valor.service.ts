import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IPresupuestoValor } from '@/shared/model/presupuesto-valor.model';

const baseApiUrl = 'api/presupuesto-valors';

export default class PresupuestoValorService {
  public find(id: number): Promise<IPresupuestoValor> {
    return new Promise<IPresupuestoValor>(resolve => {
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

  public create(entity: IPresupuestoValor): Promise<IPresupuestoValor> {
    return new Promise<IPresupuestoValor>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IPresupuestoValor): Promise<IPresupuestoValor> {
    return new Promise<IPresupuestoValor>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }
  public retrievePresupuetoProyecto(id?: number, paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get('api/presupuesto-valor-proyecto' + `/${id}` + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function (res) {
        resolve(res);
      });
    });
  }
}
