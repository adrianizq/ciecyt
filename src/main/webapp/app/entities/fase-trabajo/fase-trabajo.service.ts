import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFaseTrabajo } from 'app/shared/model/fase-trabajo.model';

type EntityResponseType = HttpResponse<IFaseTrabajo>;
type EntityArrayResponseType = HttpResponse<IFaseTrabajo[]>;

@Injectable({ providedIn: 'root' })
export class FaseTrabajoService {
  public resourceUrl = SERVER_API_URL + 'api/fase-trabajos';

  constructor(protected http: HttpClient) {}

  create(faseTrabajo: IFaseTrabajo): Observable<EntityResponseType> {
    return this.http.post<IFaseTrabajo>(this.resourceUrl, faseTrabajo, { observe: 'response' });
  }

  update(faseTrabajo: IFaseTrabajo): Observable<EntityResponseType> {
    return this.http.put<IFaseTrabajo>(this.resourceUrl, faseTrabajo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFaseTrabajo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFaseTrabajo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
