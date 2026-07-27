import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ICronogramaCiecytFases } from '@/shared/model/cronograma-ciecyt-fases.model';

const baseApiUrl = 'api/cronograma-ciecyt-fases';

export default class CronogramaCiecytFasesService {
  public find(id: number): Promise<ICronogramaCiecytFases> {
    return new Promise<ICronogramaCiecytFases>(resolve => {
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

  public create(entity: ICronogramaCiecytFases): Promise<ICronogramaCiecytFases> {
    return new Promise<ICronogramaCiecytFases>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ICronogramaCiecytFases): Promise<ICronogramaCiecytFases> {
    return new Promise<ICronogramaCiecytFases>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }
}
