import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductoProyecto } from 'app/shared/model/producto-proyecto.model';

type EntityResponseType = HttpResponse<IProductoProyecto>;
type EntityArrayResponseType = HttpResponse<IProductoProyecto[]>;

@Injectable({ providedIn: 'root' })
export class ProductoProyectoService {
  public resourceUrl = SERVER_API_URL + 'api/producto-proyectos';

  constructor(protected http: HttpClient) {}

  create(productoProyecto: IProductoProyecto): Observable<EntityResponseType> {
    return this.http.post<IProductoProyecto>(this.resourceUrl, productoProyecto, { observe: 'response' });
  }

  update(productoProyecto: IProductoProyecto): Observable<EntityResponseType> {
    return this.http.put<IProductoProyecto>(this.resourceUrl, productoProyecto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductoProyecto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductoProyecto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
