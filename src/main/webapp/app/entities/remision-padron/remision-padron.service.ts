import axios from 'axios';

import { IRemisionPadron, SolicitudRemision } from '@/shared/model/remision-padron.model';

const baseApiUrl = 'api/remisiones-padron';

export default class RemisionPadronService {
  /**
   * Remisiones ya remitidas de toda la institucion, que es lo que recibe el CIECYT.
   */
  public retrieveRemitidas(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl)
        .then(res => resolve(res))
        .catch(err => reject(err));
    });
  }

  public retrieveDeFacultad(facultadId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/facultad/${facultadId}`)
        .then(res => resolve(res))
        .catch(err => reject(err));
    });
  }

  /**
   * Arma un borrador. La lista la copia el servidor desde el padron vigente: si la enviara el
   * navegador, la remision podria no reflejar lo que de verdad se puede designar.
   */
  public crearBorrador(solicitud: SolicitudRemision): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(baseApiUrl, solicitud)
        .then(res => resolve(res))
        .catch(err => reject(err));
    });
  }

  public enviar(id: number, facultadId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/${id}/enviar?facultadId=${facultadId}`)
        .then(res => resolve(res))
        .catch(err => reject(err));
    });
  }
}
