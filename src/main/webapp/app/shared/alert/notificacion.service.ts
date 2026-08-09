import axios from 'axios';
import { INotificacion } from '@/shared/model/notificacion.model';

const baseApiUrl = 'api/notificaciones';

export default class NotificacionService {
  public findNoLeidas(): Promise<INotificacion[]> {
    return new Promise<INotificacion[]>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/no-leidas`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public marcarLeida(id: number): Promise<void> {
    return new Promise<void>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${id}/leer`)
        .then(() => {
          resolve();
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
