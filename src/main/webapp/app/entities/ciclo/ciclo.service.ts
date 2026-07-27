import axios from 'axios';

import { ICiclo } from '@/shared/model/ciclo.model';
import { IModalidad } from '@/shared/model/modalidad.model';

const baseApiUrl = 'api/ciclos';

export default class CicloService {
  public find(id: number): Promise<ICiclo> {
    return new Promise<ICiclo>(resolve => {
      axios.get(`${baseApiUrl}/${id}`).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public retrieveAll(): Promise<ICiclo[]> {
    return new Promise<ICiclo[]>(resolve => {
      axios.get(`${baseApiUrl}/all`).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public findModalidadesByCiclo(cicloId: number): Promise<IModalidad[]> {
    return new Promise<IModalidad[]>(resolve => {
      axios.get(`${baseApiUrl}/${cicloId}/modalidades`).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public create(entity: ICiclo): Promise<ICiclo> {
    return new Promise<ICiclo>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ICiclo): Promise<ICiclo> {
    return new Promise<ICiclo>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
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
}
