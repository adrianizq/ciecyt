import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGrupoSemillero } from 'app/shared/model/grupo-semillero.model';

type EntityResponseType = HttpResponse<IGrupoSemillero>;
type EntityArrayResponseType = HttpResponse<IGrupoSemillero[]>;

@Injectable({ providedIn: 'root' })
export class GrupoSemilleroService {
  public resourceUrl = SERVER_API_URL + 'api/grupo-semilleros';

  constructor(protected http: HttpClient) {}

  create(grupoSemillero: IGrupoSemillero): Observable<EntityResponseType> {
    return this.http.post<IGrupoSemillero>(this.resourceUrl, grupoSemillero, { observe: 'response' });
  }

  update(grupoSemillero: IGrupoSemillero): Observable<EntityResponseType> {
    return this.http.put<IGrupoSemillero>(this.resourceUrl, grupoSemillero, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGrupoSemillero>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGrupoSemillero[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
