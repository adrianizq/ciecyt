import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IImpactosEsperados } from 'app/shared/model/impactos-esperados.model';

type EntityResponseType = HttpResponse<IImpactosEsperados>;
type EntityArrayResponseType = HttpResponse<IImpactosEsperados[]>;

@Injectable({ providedIn: 'root' })
export class ImpactosEsperadosService {
  public resourceUrl = SERVER_API_URL + 'api/impactos-esperados';

  constructor(protected http: HttpClient) {}

  create(impactosEsperados: IImpactosEsperados): Observable<EntityResponseType> {
    return this.http.post<IImpactosEsperados>(this.resourceUrl, impactosEsperados, { observe: 'response' });
  }

  update(impactosEsperados: IImpactosEsperados): Observable<EntityResponseType> {
    return this.http.put<IImpactosEsperados>(this.resourceUrl, impactosEsperados, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IImpactosEsperados>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IImpactosEsperados[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
