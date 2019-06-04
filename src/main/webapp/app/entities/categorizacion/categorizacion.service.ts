import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICategorizacion } from 'app/shared/model/categorizacion.model';

type EntityResponseType = HttpResponse<ICategorizacion>;
type EntityArrayResponseType = HttpResponse<ICategorizacion[]>;

@Injectable({ providedIn: 'root' })
export class CategorizacionService {
  public resourceUrl = SERVER_API_URL + 'api/categorizacions';

  constructor(protected http: HttpClient) {}

  create(categorizacion: ICategorizacion): Observable<EntityResponseType> {
    return this.http.post<ICategorizacion>(this.resourceUrl, categorizacion, { observe: 'response' });
  }

  update(categorizacion: ICategorizacion): Observable<EntityResponseType> {
    return this.http.put<ICategorizacion>(this.resourceUrl, categorizacion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategorizacion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategorizacion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
