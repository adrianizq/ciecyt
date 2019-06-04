import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IElementos } from 'app/shared/model/elementos.model';

type EntityResponseType = HttpResponse<IElementos>;
type EntityArrayResponseType = HttpResponse<IElementos[]>;

@Injectable({ providedIn: 'root' })
export class ElementosService {
  public resourceUrl = SERVER_API_URL + 'api/elementos';

  constructor(protected http: HttpClient) {}

  create(elementos: IElementos): Observable<EntityResponseType> {
    return this.http.post<IElementos>(this.resourceUrl, elementos, { observe: 'response' });
  }

  update(elementos: IElementos): Observable<EntityResponseType> {
    return this.http.put<IElementos>(this.resourceUrl, elementos, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IElementos>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IElementos[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
