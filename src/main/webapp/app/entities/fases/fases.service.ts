import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IFases } from '@/shared/model/fases.model';

const baseApiUrl = 'api/fases';

export default class FasesService {
  public find(id: number): Promise<IFases> {
    return new Promise<IFases>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public findByFase(fase: string): Promise<IFases> {
    return new Promise<IFases>((resolve, reject) => {
      axios
        .get(`api/fase/${fase}`)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public retrieveFaseModalidad(fase: string, idModalidad: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`api/fase-modalidad/${fase}/${idModalidad}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }

  public retrieveFase(fase: string): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`api/fase/${fase}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }
  public retrieveFaseModalidadId(idModalidad: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`api/fase-modalidad/${idModalidad}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }

  //////buscar las fasesPorModalidad

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

  public create(entity: IFases): Promise<IFases> {
    return new Promise<IFases>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: IFases): Promise<IFases> {
    return new Promise<IFases>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }
}
