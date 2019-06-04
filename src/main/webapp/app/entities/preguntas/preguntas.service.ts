import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPreguntas } from 'app/shared/model/preguntas.model';

type EntityResponseType = HttpResponse<IPreguntas>;
type EntityArrayResponseType = HttpResponse<IPreguntas[]>;

@Injectable({ providedIn: 'root' })
export class PreguntasService {
  public resourceUrl = SERVER_API_URL + 'api/preguntas';

  constructor(protected http: HttpClient) {}

  create(preguntas: IPreguntas): Observable<EntityResponseType> {
    return this.http.post<IPreguntas>(this.resourceUrl, preguntas, { observe: 'response' });
  }

  update(preguntas: IPreguntas): Observable<EntityResponseType> {
    return this.http.put<IPreguntas>(this.resourceUrl, preguntas, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPreguntas>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPreguntas[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
