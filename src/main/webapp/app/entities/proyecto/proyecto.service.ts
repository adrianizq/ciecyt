import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IProyecto } from '@/shared/model/proyecto.model';

const baseApiUrl = 'api/proyectos';

export default class ProyectoService {
  //public proyectoId: number;

  public find(id: number): Promise<IProyecto> {
    return new Promise<IProyecto>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  public retrieveWithAsesor(idProy?: any, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('api/proyectosWithAsesor' + `/${idProy}` + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }

  public retrieveProyectoIntegrante(idUsuario?: any, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('api/proyectos-integrante' + `/${idUsuario}` + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }

  public findProyectoIntegrantes(idProyecto?: any, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('api/proyectoIntegrantes' + `/${idProyecto}` + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }

  public retrieveProyectoIntegranteAuthority(idUsuario?: any, authority?: any, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('api/proyectos-integrante' + `/${idUsuario}` + `/${authority}` + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(reject);
    });
  }

  public retrieveProyectoIntegranteRol(idUsuario?: any, rol?: any, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('api/proyectos-integrante-rol' + `/${idUsuario}` + `/${rol}` + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(function (res) {
          resolve(res);
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

  //recupera los proyectos con una lista de integrantes (diferente a la anterior )
  public retrieveAllProyectosIntegrantes(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('api/proyectosIntegrantes')
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

  //este create modifica el integrante proyecto
  public create(entity: IProyecto): Promise<IProyecto> {
    return new Promise<IProyecto>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }
  //este update modifica el integrante proyecto
  public update(entity: IProyecto): Promise<IProyecto> {
    return new Promise<IProyecto>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function (res) {
          resolve(res.data);
        })
        .catch(reject);
    });
  }

  //este crea el proyecto solo
  public createProyecto(entity: IProyecto): Promise<IProyecto> {
    return new Promise<IProyecto>((resolve, reject) => {
      axios
        .post(`api/proyects`, entity)
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
  //este update el proyecto solo
  public updateProyecto(entity: IProyecto): Promise<IProyecto> {
    return new Promise<IProyecto>((resolve, reject) => {
      axios
        .put(`api/proyects`, entity)
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

  public cambiarEstado(id: number, estado: string, observacion?: string): Promise<IProyecto> {
    return new Promise<IProyecto>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/${id}/cambiar-estado`, { estado, observacion })
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public programarActo(id: number, fecha: string): Promise<IProyecto> {
    return new Promise<IProyecto>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/${id}/acto/programar`, { fecha })
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public registrarActoRealizado(id: number): Promise<IProyecto> {
    return new Promise<IProyecto>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/${id}/acto/realizado`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public iniciarContinuidad(id: number): Promise<IProyecto> {
    return new Promise<IProyecto>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/${id}/continuidad/iniciar`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public registrarRenovacionContinuidad(id: number): Promise<IProyecto> {
    return new Promise<IProyecto>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/${id}/continuidad/renovar`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public otorgarAplazamientoContinuidad(id: number): Promise<IProyecto> {
    return new Promise<IProyecto>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/${id}/continuidad/aplazar`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
