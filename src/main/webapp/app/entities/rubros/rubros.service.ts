import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRubros } from 'app/shared/model/rubros.model';

type EntityResponseType = HttpResponse<IRubros>;
type EntityArrayResponseType = HttpResponse<IRubros[]>;

@Injectable({ providedIn: 'root' })
export class RubrosService {
  public resourceUrl = SERVER_API_URL + 'api/rubros';

  constructor(protected http: HttpClient) {}

  create(rubros: IRubros): Observable<EntityResponseType> {
    return this.http.post<IRubros>(this.resourceUrl, rubros, { observe: 'response' });
  }

  update(rubros: IRubros): Observable<EntityResponseType> {
    return this.http.put<IRubros>(this.resourceUrl, rubros, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRubros>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRubros[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
