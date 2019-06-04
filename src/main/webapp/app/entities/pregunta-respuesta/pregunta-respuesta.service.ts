import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPreguntaRespuesta } from 'app/shared/model/pregunta-respuesta.model';

type EntityResponseType = HttpResponse<IPreguntaRespuesta>;
type EntityArrayResponseType = HttpResponse<IPreguntaRespuesta[]>;

@Injectable({ providedIn: 'root' })
export class PreguntaRespuestaService {
  public resourceUrl = SERVER_API_URL + 'api/pregunta-respuestas';

  constructor(protected http: HttpClient) {}

  create(preguntaRespuesta: IPreguntaRespuesta): Observable<EntityResponseType> {
    return this.http.post<IPreguntaRespuesta>(this.resourceUrl, preguntaRespuesta, { observe: 'response' });
  }

  update(preguntaRespuesta: IPreguntaRespuesta): Observable<EntityResponseType> {
    return this.http.put<IPreguntaRespuesta>(this.resourceUrl, preguntaRespuesta, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPreguntaRespuesta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPreguntaRespuesta[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
