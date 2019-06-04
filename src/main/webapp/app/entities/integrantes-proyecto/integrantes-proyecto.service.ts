import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIntegrantesProyecto } from 'app/shared/model/integrantes-proyecto.model';

type EntityResponseType = HttpResponse<IIntegrantesProyecto>;
type EntityArrayResponseType = HttpResponse<IIntegrantesProyecto[]>;

@Injectable({ providedIn: 'root' })
export class IntegrantesProyectoService {
  public resourceUrl = SERVER_API_URL + 'api/integrantes-proyectos';

  constructor(protected http: HttpClient) {}

  create(integrantesProyecto: IIntegrantesProyecto): Observable<EntityResponseType> {
    return this.http.post<IIntegrantesProyecto>(this.resourceUrl, integrantesProyecto, { observe: 'response' });
  }

  update(integrantesProyecto: IIntegrantesProyecto): Observable<EntityResponseType> {
    return this.http.put<IIntegrantesProyecto>(this.resourceUrl, integrantesProyecto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIntegrantesProyecto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIntegrantesProyecto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
