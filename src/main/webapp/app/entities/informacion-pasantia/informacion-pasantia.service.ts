import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IInformacionPasantia } from '@/shared/model/informacion-pasantia.model';

const baseApiUrl = 'api/informacion-pasantias';

export default class InformacionPasantiaService {
  public find(id: number): Promise<IInformacionPasantia> {
    return new Promise<IInformacionPasantia>(resolve => {
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

  public create(entity: IInformacionPasantia): Promise<IInformacionPasantia> {
    return new Promise<IInformacionPasantia>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IInformacionPasantia): Promise<IInformacionPasantia> {
    return new Promise<IInformacionPasantia>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public findInformacionPasantiaProyecto(id: any): Promise<IInformacionPasantia> {
    return new Promise<IInformacionPasantia>(resolve => {
      axios.get('/api/informacion-pasantia-proyecto' + `/${id}`).then(function (res) {
        resolve(res.data);
      });
    });
  }
}
