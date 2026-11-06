import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IAdjuntoRetroalimentacion } from '@/shared/model/adjunto-retroalimentacion.model';

const baseApiUrl = 'api/adjunto-retroalimentacions';

export default class AdjuntoRetroalimentacionService {
  public find(id: number): Promise<IAdjuntoRetroalimentacion> {
    return new Promise<IAdjuntoRetroalimentacion>((resolve, reject) => {
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

  public create(entity: IAdjuntoRetroalimentacion): Promise<IAdjuntoRetroalimentacion> {
    return new Promise<IAdjuntoRetroalimentacion>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(err => {
          if (err.response) {
            console.error('Backend error:', err.response.status, err.response.data);
          }
          reject(err);
        });
    });
  }

  public update(entity: IAdjuntoRetroalimentacion): Promise<IAdjuntoRetroalimentacion> {
    return new Promise<IAdjuntoRetroalimentacion>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(err => {
          if (err.response) {
            console.error('Backend error:', err.response.status, err.response.data);
          }
          reject(err);
        });
    });
  }

  public downloadFile(id?: any, fileName?: any) {
    axios({
      url: baseApiUrl + `/downloadFile` + `/${id}`,
      method: 'GET',
      responseType: 'blob',
    }).then(response => {
      const fileURL = window.URL.createObjectURL(new Blob([response.data]));
      const fileLink = document.createElement('a');

      fileLink.href = fileURL;
      //fileLink.setAttribute('download', 'file.docx');
      fileLink.setAttribute('download', fileName);
      document.body.appendChild(fileLink);

      fileLink.click();
    });
  }

  public previewFile(id?: any, fileName?: string) {
    // Abrir la ventana en el gesto del usuario evita el bloqueo de popups y la descarga forzada.
    const ventana = window.open('', '_blank');
    return axios({
      url: baseApiUrl + `/downloadFile` + `/${id}`,
      method: 'GET',
      responseType: 'blob',
    })
      .then(response => {
        const headers: any = response.headers || {};
        const headerType = String(headers['content-type'] || (headers.get ? headers.get('content-type') : '') || '');
        const contentType = this.tipoContenidoArchivo(fileName) || headerType || 'application/octet-stream';
        const blob = new Blob([response.data], { type: contentType });
        const fileURL = window.URL.createObjectURL(blob);
        if (ventana) {
          ventana.location.href = fileURL;
        } else {
          const fileLink = document.createElement('a');
          fileLink.href = fileURL;
          fileLink.setAttribute('download', fileName || 'archivo');
          document.body.appendChild(fileLink);
          fileLink.click();
          document.body.removeChild(fileLink);
        }
        setTimeout(() => window.URL.revokeObjectURL(fileURL), 60000);
      })
      .catch(err => {
        if (ventana) {
          ventana.close();
        }
        throw err;
      });
  }

  private tipoContenidoArchivo(fileName?: string): string {
    const nombre = String(fileName || '').toLowerCase();
    if (nombre.endsWith('.pdf')) {
      return 'application/pdf';
    }
    if (nombre.endsWith('.png')) {
      return 'image/png';
    }
    if (nombre.endsWith('.jpg') || nombre.endsWith('.jpeg')) {
      return 'image/jpeg';
    }
    if (nombre.endsWith('.gif')) {
      return 'image/gif';
    }
    if (nombre.endsWith('.webp')) {
      return 'image/webp';
    }
    if (nombre.endsWith('.txt')) {
      return 'text/plain';
    }
    if (nombre.endsWith('.html') || nombre.endsWith('.htm')) {
      return 'text/html';
    }
    return '';
  }

  public findAdjuntoRetroalimentacionProyectoFaseAuthority(idProyecto?: any, idFase?: any, authority?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + `/traer` + `/${idProyecto}` + `/${idFase}` + `/${authority}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }
}
