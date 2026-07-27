import axios from 'axios';

//import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IPredicciones } from '@/shared/model/predicciones.model';

//const baseApiUrl = 'api/proyectopredict';

export default class PrediccionesService {
  //public proyectoId: number;

  public retrieve(tipo: number): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get('api/proyectopredict' + `/${tipo}`).then(function (res) {
        resolve(res);
      });
    });
  }
}
