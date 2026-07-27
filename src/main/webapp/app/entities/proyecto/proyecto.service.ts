import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IProyecto } from '@/shared/model/proyecto.model';

const baseApiUrl = 'api/proyectos';

export default class ProyectoService {
  //public proyectoId: number;

  public find(id: number): Promise<IProyecto> {
    return new Promise<IProyecto>(resolve => {
      axios.get(`${baseApiUrl}/${id}`).then(function (res) {
        resolve(res.data);
      });
    });
  }

  public retrieveWithAsesor(idProy?: any, paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get('api/proyectosWithAsesor' + `/${idProy}` + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function (res) {
        resolve(res);
      });
    });
  }

  public retrieveProyectoIntegrante(idUsuario?: any, paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get('api/proyectos-integrante' + `/${idUsuario}` + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function (res) {
        resolve(res);
      });
    });
  }

  public findProyectoIntegrantes(idProyecto?: any, paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get('api/proyectoIntegrantes' + `/${idProyecto}` + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function (res) {
        resolve(res);
      });
    });
  }

  public retrieveProyectoIntegranteAuthority(idUsuario?: any, authority?: any, paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios
        .get('api/proyectos-integrante' + `/${idUsuario}` + `/${authority}` + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(function (res) {
          resolve(res);
        });
    });
  }

  public retrieveProyectoIntegranteRol(idUsuario?: any, rol?: any, paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios
        .get('api/proyectos-integrante-rol' + `/${idUsuario}` + `/${rol}` + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(function (res) {
          resolve(res);
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

  //recupera los proyectos con una lista de integrantes (diferente a la anterior )
  public retrieveAllProyectosIntegrantes(paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get('api/proyectosIntegrantes').then(function (res) {
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

  //este create modifica el integrante proyecto
  public create(entity: IProyecto): Promise<IProyecto> {
    return new Promise<IProyecto>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }
  //este update modifica el integrante proyecto
  public update(entity: IProyecto): Promise<IProyecto> {
    return new Promise<IProyecto>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function (res) {
        resolve(res.data);
      });
    });
  }

  //este crea el proyecto solo
  public createProyecto(entity: IProyecto): Promise<IProyecto> {
    return new Promise<IProyecto>((resolve, reject) => {
      axios.post(`api/proyects`, entity).then(function (res) {
        resolve(res.data);
      }).catch(err => {
        if (err.response) {
          console.error('Backend error:', err.response.status, err.response.data);
        }
        reject(err);
      });
    });
  }
  //este update el proyecto solo
  public updateProyecto(entity: IProyecto): Promise<IProyecto> {
    return new Promise<IProyecto>((resolve, reject) => {
      axios.put(`api/proyects`, entity).then(function (res) {
        resolve(res.data);
      }).catch(err => {
        if (err.response) {
          console.error('Backend error:', err.response.status, err.response.data);
        }
        reject(err);
      });
    });
  }

  public retrieveSearchTitulo(cad: any, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${cad}/searchtitulo` + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieveSearchPrograma(cad: any, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${cad}/searchprograma` + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
