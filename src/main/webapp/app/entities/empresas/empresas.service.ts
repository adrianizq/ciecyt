import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEmpresas } from 'app/shared/model/empresas.model';

type EntityResponseType = HttpResponse<IEmpresas>;
type EntityArrayResponseType = HttpResponse<IEmpresas[]>;

@Injectable({ providedIn: 'root' })
export class EmpresasService {
  public resourceUrl = SERVER_API_URL + 'api/empresas';

  constructor(protected http: HttpClient) {}

  create(empresas: IEmpresas): Observable<EntityResponseType> {
    return this.http.post<IEmpresas>(this.resourceUrl, empresas, { observe: 'response' });
  }

  update(empresas: IEmpresas): Observable<EntityResponseType> {
    return this.http.put<IEmpresas>(this.resourceUrl, empresas, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmpresas>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmpresas[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
