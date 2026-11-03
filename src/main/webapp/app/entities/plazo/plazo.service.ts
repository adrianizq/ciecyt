import axios from 'axios';

export default class PlazoService {
  public verificar(): Promise<{ notificacionesCreadas: number }> {
    return axios.post('api/plazos/verificar').then(res => res.data);
  }
}
