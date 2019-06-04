import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoPregunta } from 'app/shared/model/tipo-pregunta.model';

type EntityResponseType = HttpResponse<ITipoPregunta>;
type EntityArrayResponseType = HttpResponse<ITipoPregunta[]>;

@Injectable({ providedIn: 'root' })
export class TipoPreguntaService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-preguntas';

  constructor(protected http: HttpClient) {}

  create(tipoPregunta: ITipoPregunta): Observable<EntityResponseType> {
    return this.http.post<ITipoPregunta>(this.resourceUrl, tipoPregunta, { observe: 'response' });
  }

  update(tipoPregunta: ITipoPregunta): Observable<EntityResponseType> {
    return this.http.put<ITipoPregunta>(this.resourceUrl, tipoPregunta, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoPregunta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoPregunta[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
