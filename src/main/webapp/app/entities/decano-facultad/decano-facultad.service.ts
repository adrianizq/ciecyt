import axios from 'axios';

/**
 * Servicio para la gestion de decano_facultad desde la pantalla admin
 * (/admin/decano-facultad). Solo ROLE_ADMIN escribe; cualquier decano en
 * sesion consulta sus propias facultades con {@link misFacultades}.
 *
 * El backend expone:
 *   GET    /api/decanos-facultad                          (admin)
 *   GET    /api/decanos-facultad/{id}                     (admin)
 *   GET    /api/decanos-facultad/facultad/{facultadId}    (admin o decano vigente)
 *   GET    /api/decanos-facultad/mis-facultades           (decano/ciecyt/admin en sesion)
 *   POST   /api/decanos-facultad                          (admin)
 *   PUT    /api/decanos-facultad                          (admin)
 *   POST   /api/decanos-facultad/facultad/{id}/cerrar     (admin)
 *   DELETE /api/decanos-facultad/{id}                     (admin)
 */
export default class DecanoFacultadService {
  public retrieve(): Promise<any> {
    return axios.get('api/decanos-facultad');
  }

  public find(id: number): Promise<any> {
    return axios.get(`api/decanos-facultad/${id}`);
  }

  public findByFacultad(facultadId: number): Promise<any> {
    return axios.get(`api/decanos-facultad/facultad/${facultadId}`);
  }

  public misFacultades(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('api/decanos-facultad/mis-facultades')
        .then(res => resolve(res))
        .catch(err => reject(err));
    });
  }

  public create(payload: any): Promise<any> {
    return axios.post('api/decanos-facultad', payload);
  }

  public update(payload: any): Promise<any> {
    return axios.put('api/decanos-facultad', payload);
  }

  public cerrarVigencia(facultadId: number, fechaHasta: string): Promise<any> {
    return axios.post(`api/decanos-facultad/facultad/${facultadId}/cerrar`, JSON.stringify(fechaHasta), {
      headers: { 'content-type': 'application/json' },
    });
  }

  public delete(id: number): Promise<any> {
    return axios.delete(`api/decanos-facultad/${id}`);
  }
}
