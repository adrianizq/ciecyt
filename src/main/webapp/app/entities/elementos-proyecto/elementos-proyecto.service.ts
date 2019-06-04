import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IElementosProyecto } from 'app/shared/model/elementos-proyecto.model';

type EntityResponseType = HttpResponse<IElementosProyecto>;
type EntityArrayResponseType = HttpResponse<IElementosProyecto[]>;

@Injectable({ providedIn: 'root' })
export class ElementosProyectoService {
  public resourceUrl = SERVER_API_URL + 'api/elementos-proyectos';

  constructor(protected http: HttpClient) {}

  create(elementosProyecto: IElementosProyecto): Observable<EntityResponseType> {
    return this.http.post<IElementosProyecto>(this.resourceUrl, elementosProyecto, { observe: 'response' });
  }

  update(elementosProyecto: IElementosProyecto): Observable<EntityResponseType> {
    return this.http.put<IElementosProyecto>(this.resourceUrl, elementosProyecto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IElementosProyecto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IElementosProyecto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
