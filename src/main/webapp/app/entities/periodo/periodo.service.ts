import axios from 'axios';
import { IPeriodo } from '@/shared/model/periodo.model';

const baseApiUrl = 'api/periodos';

export default class PeriodoService {
  public retrieveAll(): Promise<IPeriodo[]> {
    return axios.get(baseApiUrl).then(res => res.data);
  }

  public create(entity: IPeriodo): Promise<IPeriodo> {
    return axios.post(baseApiUrl, entity).then(res => res.data);
  }

  public update(entity: IPeriodo): Promise<IPeriodo> {
    return axios.put(baseApiUrl, entity).then(res => res.data);
  }

  public delete(id: number): Promise<void> {
    return axios.delete(`${baseApiUrl}/${id}`).then(() => undefined);
  }
}
