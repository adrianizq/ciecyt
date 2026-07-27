import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IIntegranteProyecto } from '@/shared/model/integrante-proyecto.model';

const baseApiUrl = 'api/integrante-proyectos';

export default class IntegranteProyectoService {
  public find(id: number): Promise<IIntegranteProyecto> {
    return new Promise<IIntegranteProyecto>(resolve => {
      axios.get(`${baseApiUrl}/${id}`).then(function (res) {
        resolve(res.data);
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

  public retrieveEstudiantes(idProyecto?: number, idRolModalidad?: number, paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios
        .get(
          '/api/integrante-proyectos-proyecto-modalidad' +
            `/${idProyecto}` +
            `/${idRolModalidad}` +
            `?${buildPaginationQueryOpts(paginationQuery)}`
        )
        .then(function (res) {
          resolve(res);
        });
    });
  }

  public retrieveEstudiantesProyecto(idProyecto?: number, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('/api/integrante-proyectos-estudiantes' + `/${idProyecto}` + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(function (err) {
          reject(err);
        });
    });
  }
  public retrieveJuradosProyecto(idProyecto?: number, tipoJurado?: string, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('/api/integrante-proyectos-jurados' + `/${idProyecto}/${tipoJurado}` + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(function (err) {
          reject(err);
        });
    });
  }

  public retrieveJuradosProyectoNoPage(idProyecto?: number, tipoJurado?: string): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios.get('/api/integrante-proyectos-jurados' + `/${idProyecto}/${tipoJurado}`).then(function (res) {
        resolve(res);
      }).catch(function (err) {
        reject(err);
      });
    });
  }

  public retrieveJurados(paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get('/api/users/jurados' + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function (res) {
        resolve(res);
      });
    });
  }

  public retrieveAsesoresProyecto(idProyecto?: number, paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('/api/integrante-proyectos-asesores' + `/${idProyecto}` + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(function (res) {
          resolve(res);
        })
        .catch(function (err) {
          reject(err);
        });
    });
  }

  public retrieveAsesores(paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get('/api/users/asesores' + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function (res) {
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

  public create(entity: IIntegranteProyecto): Promise<IIntegranteProyecto> {
    return new Promise<IIntegranteProyecto>((resolve, reject) => {
      axios.post(`${baseApiUrl}`, entity).then(
        function (res) {
          resolve(res.data);
        },
        function (err) {
          reject(err);
        }
      );
    });
  }

  public update(entity: IIntegranteProyecto): Promise<IIntegranteProyecto> {
    return new Promise<IIntegranteProyecto>((resolve, reject) => {
      axios.put(`${baseApiUrl}`, entity).then(
        function (res) {
          resolve(res.data);
        },
        function (err) {
          reject(err);
        }
      );
    });
  }
}
