import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFacultad } from 'app/shared/model/facultad.model';

type EntityResponseType = HttpResponse<IFacultad>;
type EntityArrayResponseType = HttpResponse<IFacultad[]>;

@Injectable({ providedIn: 'root' })
export class FacultadService {
  public resourceUrl = SERVER_API_URL + 'api/facultads';

  constructor(protected http: HttpClient) {}

  create(facultad: IFacultad): Observable<EntityResponseType> {
    return this.http.post<IFacultad>(this.resourceUrl, facultad, { observe: 'response' });
  }

  update(facultad: IFacultad): Observable<EntityResponseType> {
    return this.http.put<IFacultad>(this.resourceUrl, facultad, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFacultad>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFacultad[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
