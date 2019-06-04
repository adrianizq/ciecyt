import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IResultadosEsperados } from 'app/shared/model/resultados-esperados.model';

type EntityResponseType = HttpResponse<IResultadosEsperados>;
type EntityArrayResponseType = HttpResponse<IResultadosEsperados[]>;

@Injectable({ providedIn: 'root' })
export class ResultadosEsperadosService {
  public resourceUrl = SERVER_API_URL + 'api/resultados-esperados';

  constructor(protected http: HttpClient) {}

  create(resultadosEsperados: IResultadosEsperados): Observable<EntityResponseType> {
    return this.http.post<IResultadosEsperados>(this.resourceUrl, resultadosEsperados, { observe: 'response' });
  }

  update(resultadosEsperados: IResultadosEsperados): Observable<EntityResponseType> {
    return this.http.put<IResultadosEsperados>(this.resourceUrl, resultadosEsperados, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResultadosEsperados>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResultadosEsperados[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
