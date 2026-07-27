import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IInvestigacionTipo } from '@/shared/model/investigacion-tipo.model';

const baseApiUrl = 'api/investigacion-tipos';

export default class InvestigacionTipoService {
  public find(id: number): Promise<IInvestigacionTipo> {
    return new Promise<IInvestigacionTipo>(resolve => {
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

  public create(entity: IInvestigacionTipo): Promise<IInvestigacionTipo> {
    return new Promise<IInvestigacionTipo>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IInvestigacionTipo): Promise<IInvestigacionTipo> {
    return new Promise<IInvestigacionTipo>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }
}
