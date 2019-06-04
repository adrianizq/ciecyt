import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IModalidadDeGrado } from 'app/shared/model/modalidad-de-grado.model';

type EntityResponseType = HttpResponse<IModalidadDeGrado>;
type EntityArrayResponseType = HttpResponse<IModalidadDeGrado[]>;

@Injectable({ providedIn: 'root' })
export class ModalidadDeGradoService {
  public resourceUrl = SERVER_API_URL + 'api/modalidad-de-grados';

  constructor(protected http: HttpClient) {}

  create(modalidadDeGrado: IModalidadDeGrado): Observable<EntityResponseType> {
    return this.http.post<IModalidadDeGrado>(this.resourceUrl, modalidadDeGrado, { observe: 'response' });
  }

  update(modalidadDeGrado: IModalidadDeGrado): Observable<EntityResponseType> {
    return this.http.put<IModalidadDeGrado>(this.resourceUrl, modalidadDeGrado, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IModalidadDeGrado>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IModalidadDeGrado[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
