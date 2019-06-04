import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPresupuestoValor } from 'app/shared/model/presupuesto-valor.model';

type EntityResponseType = HttpResponse<IPresupuestoValor>;
type EntityArrayResponseType = HttpResponse<IPresupuestoValor[]>;

@Injectable({ providedIn: 'root' })
export class PresupuestoValorService {
  public resourceUrl = SERVER_API_URL + 'api/presupuesto-valors';

  constructor(protected http: HttpClient) {}

  create(presupuestoValor: IPresupuestoValor): Observable<EntityResponseType> {
    return this.http.post<IPresupuestoValor>(this.resourceUrl, presupuestoValor, { observe: 'response' });
  }

  update(presupuestoValor: IPresupuestoValor): Observable<EntityResponseType> {
    return this.http.put<IPresupuestoValor>(this.resourceUrl, presupuestoValor, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPresupuestoValor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPresupuestoValor[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
