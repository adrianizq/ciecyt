import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRolesUsuarioProyecto } from 'app/shared/model/roles-usuario-proyecto.model';

type EntityResponseType = HttpResponse<IRolesUsuarioProyecto>;
type EntityArrayResponseType = HttpResponse<IRolesUsuarioProyecto[]>;

@Injectable({ providedIn: 'root' })
export class RolesUsuarioProyectoService {
  public resourceUrl = SERVER_API_URL + 'api/roles-usuario-proyectos';

  constructor(protected http: HttpClient) {}

  create(rolesUsuarioProyecto: IRolesUsuarioProyecto): Observable<EntityResponseType> {
    return this.http.post<IRolesUsuarioProyecto>(this.resourceUrl, rolesUsuarioProyecto, { observe: 'response' });
  }

  update(rolesUsuarioProyecto: IRolesUsuarioProyecto): Observable<EntityResponseType> {
    return this.http.put<IRolesUsuarioProyecto>(this.resourceUrl, rolesUsuarioProyecto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRolesUsuarioProyecto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRolesUsuarioProyecto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
