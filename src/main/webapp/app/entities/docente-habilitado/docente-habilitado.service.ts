import axios from 'axios';

import { IDocenteHabilitado, SolicitudHabilitacion } from '@/shared/model/docente-habilitado.model';

const baseApiUrl = 'api/docente-habilitados';

export default class DocenteHabilitadoService {
  /**
   * Docentes habilitados de una facultad, opcionalmente de un solo rol. Es la lista de la que el
   * CIECYT designa: offering cualquier ROLE_ASESOR permitiria escolher a alguien que la decanatura
   * no ha habilitado.
   */
  public retrieveDeFacultad(facultadId: number, rol?: string): Promise<any> {
    const filtro = rol ? `?rol=${encodeURIComponent(rol)}` : '';
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/facultad/${facultadId}${filtro}`)
        .then(res => resolve(res))
        .catch(err => reject(err));
    });
  }

  /**
   * Historial de una persona en una facultad. Se pide por facultad porque la habilitacion es por
   * facultad y quien consulta solo puede ver las autorizadas: pedirlo por persona y sin facultad
   * dejaria ver las habilitaciones de otras facultades.
   */
  public retrieveHistorial(facultadId: number, userId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/facultad/${facultadId}/historial/${userId}`)
        .then(res => resolve(res))
        .catch(err => reject(err));
    });
  }

  public habilitar(solicitud: SolicitudHabilitacion): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(baseApiUrl, solicitud)
        .then(res => resolve(res))
        .catch(err => reject(err));
    });
  }

  public cerrar(solicitud: SolicitudHabilitacion): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/cerrar`, solicitud)
        .then(res => resolve(res))
        .catch(err => reject(err));
    });
  }
}
