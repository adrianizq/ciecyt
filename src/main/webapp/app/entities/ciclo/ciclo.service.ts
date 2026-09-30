import axios from 'axios';

import { ICiclo } from '@/shared/model/ciclo.model';
import { IModalidad } from '@/shared/model/modalidad.model';

const baseApiUrl = 'api/ciclos';

export default class CicloService {
  public find(id: number): Promise<ICiclo> {
    return new Promise<ICiclo>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public retrieveAll(): Promise<ICiclo[]> {
    return new Promise<ICiclo[]>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/all`)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public findModalidadesByCiclo(cicloId: number): Promise<IModalidad[]> {
    return new Promise<IModalidad[]>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${cicloId}/modalidades`)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public create(entity: ICiclo): Promise<ICiclo> {
    return new Promise<ICiclo>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: ICiclo): Promise<ICiclo> {
    return new Promise<ICiclo>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
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
}
