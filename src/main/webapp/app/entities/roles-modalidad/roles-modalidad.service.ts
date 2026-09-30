import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IRolesModalidad } from '@/shared/model/roles-modalidad.model';

const baseApiUrl = 'api/roles-modalidads';

export default class RolesModalidadService {
  public find(id: number): Promise<IRolesModalidad> {
    return new Promise<IRolesModalidad>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public findRolModalidad(rol: any, idModalidad: number): Promise<IRolesModalidad> {
    return new Promise<IRolesModalidad>((resolve, reject) => {
      axios
        .get(`/api/roles-modalidad-rol-modalidad/${rol}/${idModalidad}`)
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

  public create(entity: IRolesModalidad): Promise<IRolesModalidad> {
    return new Promise<IRolesModalidad>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: IRolesModalidad): Promise<IRolesModalidad> {
    return new Promise<IRolesModalidad>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }
}
