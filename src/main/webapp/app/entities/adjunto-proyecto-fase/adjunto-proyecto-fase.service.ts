import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IAdjuntoProyectoFase } from '@/shared/model/adjunto-proyecto-fase.model';

const baseApiUrl = 'api/adjunto-proyecto-fases';

export default class AdjuntoProyectoFaseService {
  public find(id: number): Promise<IAdjuntoProyectoFase> {
    return new Promise<IAdjuntoProyectoFase>((resolve, reject) => {
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

  public create(entity: IAdjuntoProyectoFase): Promise<IAdjuntoProyectoFase> {
    return new Promise<IAdjuntoProyectoFase>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public update(entity: IAdjuntoProyectoFase): Promise<IAdjuntoProyectoFase> {
    return new Promise<IAdjuntoProyectoFase>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public downloadFile(id?: any, fileName?: any) {
    axios({
      url: baseApiUrl + `/downloadFile` + `/${id}`,
      method: 'GET',
      responseType: 'blob',
    }).then(response => {
      var fileURL = window.URL.createObjectURL(new Blob([response.data]));
      var fileLink = document.createElement('a');

      fileLink.href = fileURL;
      //fileLink.setAttribute('download', 'file.docx');
      fileLink.setAttribute('download', fileName);
      document.body.appendChild(fileLink);

      fileLink.click();
    });
  }

  public findAdjuntoProyectoFase(idProyecto?: any, idFase?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + `/traer` + `/${idProyecto}` + `/${idFase}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }

  /*public findAdjuntoProyectoFase(idProyecto?: any, idFase?: any): Promise<IAdjuntoProyectoFase> {
    return new Promise<IAdjuntoProyectoFase>((resolve, reject) => {
      axios.get(baseApiUrl + `/traer` + `/${idProyecto}` + `/${idFase}`).then(function (res) {
        resolve(res.data);
      }).catch(reject);
    });
  }*/
}
