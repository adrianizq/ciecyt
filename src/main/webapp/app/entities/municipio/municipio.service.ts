import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IMunicipio } from '@/shared/model/municipio.model';

const baseApiUrl = 'api/municipios';

export default class MunicipioService {
  public find(id: number): Promise<IMunicipio> {
    return new Promise<IMunicipio>(resolve => {
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

  public retrieveNoPage(): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get('api/municipios-no-page').then(function (res) {
        resolve(res);
      });
    });
  }

  public retrieveMunicipiosPorDepartamento(dep: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get('api/municipios-departamento' + `/${dep}`).then(function (res) {
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

  public create(entity: IMunicipio): Promise<IMunicipio> {
    return new Promise<IMunicipio>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IMunicipio): Promise<IMunicipio> {
    return new Promise<IMunicipio>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }
}
