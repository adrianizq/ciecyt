import axios from 'axios';
import { IFestivo, IResultadoImportacionFestivos } from '@/shared/model/festivo.model';

const baseApiUrl = 'api/festivos';

export default class FestivoService {
  public retrieveAll(): Promise<IFestivo[]> {
    return axios.get(baseApiUrl).then(res => res.data);
  }

  public create(entity: IFestivo): Promise<IFestivo> {
    return axios.post(baseApiUrl, entity).then(res => res.data);
  }

  public update(entity: IFestivo): Promise<IFestivo> {
    return axios.put(baseApiUrl, entity).then(res => res.data);
  }

  public delete(id: number): Promise<void> {
    return axios.delete(`${baseApiUrl}/${id}`).then(() => undefined);
  }

  public importar(contenido: string): Promise<IResultadoImportacionFestivos> {
    return axios.post(`${baseApiUrl}/importar`, { contenido }).then(res => res.data);
  }

  public descargarPlantilla(): Promise<Blob> {
    return axios.get(`${baseApiUrl}/plantilla`, { responseType: 'blob' }).then(res => res.data);
  }
}
