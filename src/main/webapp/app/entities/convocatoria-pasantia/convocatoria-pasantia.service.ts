import axios from 'axios';
import { IConvocatoriaPasantia } from '@/shared/model/convocatoria-pasantia.model';
import { IPostulacionPasantia } from '@/shared/model/postulacion-pasantia.model';

const baseApiUrl = 'api/convocatorias-pasantia';

export default class ConvocatoriaPasantiaService {
  public findAll(): Promise<IConvocatoriaPasantia[]> {
    return axios.get(baseApiUrl).then(res => res.data);
  }

  public findAbiertas(): Promise<IConvocatoriaPasantia[]> {
    return axios.get(`${baseApiUrl}/abiertas`).then(res => res.data);
  }

  public findMisPostulaciones(): Promise<IPostulacionPasantia[]> {
    return axios.get(`${baseApiUrl}/mis-postulaciones`).then(res => res.data);
  }

  public findPostulaciones(convocatoriaId: number): Promise<IPostulacionPasantia[]> {
    return axios.get(`${baseApiUrl}/${convocatoriaId}/postulaciones`).then(res => res.data);
  }

  public create(entity: IConvocatoriaPasantia): Promise<IConvocatoriaPasantia> {
    return axios.post(baseApiUrl, entity).then(res => res.data);
  }

  public update(entity: IConvocatoriaPasantia): Promise<IConvocatoriaPasantia> {
    return axios.put(baseApiUrl, entity).then(res => res.data);
  }

  public cambiarEstado(id: number, estado: string): Promise<IConvocatoriaPasantia> {
    return axios.post(`${baseApiUrl}/${id}/estado`, { estado }).then(res => res.data);
  }

  public delete(id: number): Promise<void> {
    return axios.delete(`${baseApiUrl}/${id}`).then(() => undefined);
  }

  public postular(convocatoriaId: number, proyectoId: number, observacion?: string): Promise<IPostulacionPasantia> {
    return axios.post(`${baseApiUrl}/${convocatoriaId}/postular`, { proyectoId, observacion }).then(res => res.data);
  }

  public seleccionar(postulacionId: number): Promise<IPostulacionPasantia> {
    return axios.post(`${baseApiUrl}/postulaciones/${postulacionId}/seleccionar`).then(res => res.data);
  }

  public rechazar(postulacionId: number, observacion?: string): Promise<IPostulacionPasantia> {
    return axios.post(`${baseApiUrl}/postulaciones/${postulacionId}/rechazar`, { observacion }).then(res => res.data);
  }
}
