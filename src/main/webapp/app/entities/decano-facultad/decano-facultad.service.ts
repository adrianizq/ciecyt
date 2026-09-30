import axios from 'axios';

/**
 * Facultades que el usuario en sesion administra como decano. La pantalla de decanura no recibe la
 * facultad por parametro: la resuelve sola, para que un decano no pueda ver el padron de otra
 * escribiendo otro id en la barra de direcciones.
 */
export default class DecanoFacultadService {
  public misFacultades(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('api/decanos-facultad/mis-facultades')
        .then(res => resolve(res))
        .catch(err => reject(err));
    });
  }
}
