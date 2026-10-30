import axios from 'axios';
import { IProyectoHistorialEstado } from '@/shared/model/proyecto-historial-estado.model';

const baseApiUrl = 'api/proyecto-historial-estados';

export default class ProyectoHistorialEstadoService {
  public findByProyecto(proyectoId: number): Promise<IProyectoHistorialEstado[]> {
    return new Promise<IProyectoHistorialEstado[]>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/proyecto/${proyectoId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(reject);
    });
  }
}
