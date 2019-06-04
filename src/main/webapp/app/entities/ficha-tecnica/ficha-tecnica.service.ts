import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFichaTecnica } from 'app/shared/model/ficha-tecnica.model';

type EntityResponseType = HttpResponse<IFichaTecnica>;
type EntityArrayResponseType = HttpResponse<IFichaTecnica[]>;

@Injectable({ providedIn: 'root' })
export class FichaTecnicaService {
  public resourceUrl = SERVER_API_URL + 'api/ficha-tecnicas';

  constructor(protected http: HttpClient) {}

  create(fichaTecnica: IFichaTecnica): Observable<EntityResponseType> {
    return this.http.post<IFichaTecnica>(this.resourceUrl, fichaTecnica, { observe: 'response' });
  }

  update(fichaTecnica: IFichaTecnica): Observable<EntityResponseType> {
    return this.http.put<IFichaTecnica>(this.resourceUrl, fichaTecnica, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFichaTecnica>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFichaTecnica[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
