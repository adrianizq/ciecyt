import axios from 'axios';

import { IAsesorExterno, SolicitudAsesorExterno } from '@/shared/model/asesor-externo.model';

const baseApiUrl = 'api/asesores-externos';

export default class AsesorExternoService {
  /**
   * Profesionales externos registrados en una facultad. Con el parametro rol la lista se
   * reduce a los que ya verifico el CIECYT para ese cargo, que es lo que se ofrece al
   * designar: el backend rechaza a un externo sin idoneidad verificada.
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

  public find(id: number): Promise<IAsesorExterno> {
    return new Promise<IAsesorExterno>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => resolve(res.data))
        .catch(err => reject(err));
    });
  }

  public registrar(solicitud: SolicitudAsesorExterno): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(baseApiUrl, solicitud)
        .then(res => resolve(res))
        .catch(err => reject(err));
    });
  }

  public actualizar(id: number, datos: IAsesorExterno): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${id}`, datos)
        .then(res => resolve(res))
        .catch(err => reject(err));
    });
  }

  public verificar(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/${id}/verificar`)
        .then(res => resolve(res))
        .catch(err => reject(err));
    });
  }
}
