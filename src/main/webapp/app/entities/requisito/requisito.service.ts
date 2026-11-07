import axios from 'axios';

import { IRequisito } from '@/shared/model/requisito.model';

const baseApiUrl = 'api/requisitos';

export default class RequisitoService {
  public retrieve(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl, { params: { size: 500, sort: 'codigo,asc' } })
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }

  public find(id: number): Promise<IRequisito> {
    return new Promise<IRequisito>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: IRequisito): Promise<IRequisito> {
    return new Promise<IRequisito>((resolve, reject) => {
      axios
        .put(baseApiUrl, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public deleteFormato(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}/formato`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }

  public downloadFormato(id?: any, fileName?: any) {
    axios({
      url: `${baseApiUrl}/${id}/formato`,
      method: 'GET',
      responseType: 'blob',
    }).then(response => {
      const fileURL = window.URL.createObjectURL(new Blob([response.data]));
      const fileLink = document.createElement('a');
      fileLink.href = fileURL;
      fileLink.setAttribute('download', fileName || 'formato-requisito');
      document.body.appendChild(fileLink);
      fileLink.click();
      document.body.removeChild(fileLink);
      setTimeout(() => window.URL.revokeObjectURL(fileURL), 60000);
    });
  }
}
