import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IMunicipio } from '@/shared/model/municipio.model';

const baseApiUrl = 'api/municipios';

export default class MunicipioService {
  public find(id: number): Promise<IMunicipio> {
    return new Promise<IMunicipio>((resolve, reject) => {
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

  public retrieveNoPage(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('api/municipios-no-page')
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }

  public retrieveMunicipiosPorDepartamento(dep: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('api/municipios-departamento' + `/${dep}`)
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

  public create(entity: IMunicipio): Promise<IMunicipio> {
    return new Promise<IMunicipio>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: IMunicipio): Promise<IMunicipio> {
    return new Promise<IMunicipio>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }
}
