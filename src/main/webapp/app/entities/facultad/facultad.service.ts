import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IFacultad } from '@/shared/model/facultad.model';

const baseApiUrl = 'api/facultads';

export default class FacultadService {
  public find(id: number): Promise<IFacultad> {
    return new Promise<IFacultad>(resolve => {
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

  public create(entity: IFacultad): Promise<IFacultad> {
    return new Promise<IFacultad>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IFacultad): Promise<IFacultad> {
    return new Promise<IFacultad>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }
}
