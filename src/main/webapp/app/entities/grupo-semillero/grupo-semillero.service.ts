import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IGrupoSemillero } from '@/shared/model/grupo-semillero.model';

const baseApiUrl = 'api/grupo-semilleros';

export default class GrupoSemilleroService {
  public find(id: number): Promise<IGrupoSemillero> {
    return new Promise<IGrupoSemillero>((resolve, reject) => {
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

  public create(entity: IGrupoSemillero): Promise<IGrupoSemillero> {
    return new Promise<IGrupoSemillero>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: IGrupoSemillero): Promise<IGrupoSemillero> {
    return new Promise<IGrupoSemillero>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }
}
