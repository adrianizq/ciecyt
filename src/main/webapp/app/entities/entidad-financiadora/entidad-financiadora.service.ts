import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEntidadFinanciadora } from 'app/shared/model/entidad-financiadora.model';

type EntityResponseType = HttpResponse<IEntidadFinanciadora>;
type EntityArrayResponseType = HttpResponse<IEntidadFinanciadora[]>;

@Injectable({ providedIn: 'root' })
export class EntidadFinanciadoraService {
  public resourceUrl = SERVER_API_URL + 'api/entidad-financiadoras';

  constructor(protected http: HttpClient) {}

  create(entidadFinanciadora: IEntidadFinanciadora): Observable<EntityResponseType> {
    return this.http.post<IEntidadFinanciadora>(this.resourceUrl, entidadFinanciadora, { observe: 'response' });
  }

  update(entidadFinanciadora: IEntidadFinanciadora): Observable<EntityResponseType> {
    return this.http.put<IEntidadFinanciadora>(this.resourceUrl, entidadFinanciadora, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEntidadFinanciadora>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEntidadFinanciadora[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
