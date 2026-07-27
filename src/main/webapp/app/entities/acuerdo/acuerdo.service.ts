import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IAcuerdo } from '@/shared/model/acuerdo.model';

const baseApiUrl = 'api/acuerdos';

export default class AcuerdoService {
  public find(id: number): Promise<IAcuerdo> {
    return new Promise<IAcuerdo>(resolve => {
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

  public create(entity: IAcuerdo): Promise<IAcuerdo> {
    return new Promise<IAcuerdo>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IAcuerdo): Promise<IAcuerdo> {
    return new Promise<IAcuerdo>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }
}
