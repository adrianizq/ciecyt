import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILineaDeInvestigacion } from 'app/shared/model/linea-de-investigacion.model';

type EntityResponseType = HttpResponse<ILineaDeInvestigacion>;
type EntityArrayResponseType = HttpResponse<ILineaDeInvestigacion[]>;

@Injectable({ providedIn: 'root' })
export class LineaDeInvestigacionService {
  public resourceUrl = SERVER_API_URL + 'api/linea-de-investigacions';

  constructor(protected http: HttpClient) {}

  create(lineaDeInvestigacion: ILineaDeInvestigacion): Observable<EntityResponseType> {
    return this.http.post<ILineaDeInvestigacion>(this.resourceUrl, lineaDeInvestigacion, { observe: 'response' });
  }

  update(lineaDeInvestigacion: ILineaDeInvestigacion): Observable<EntityResponseType> {
    return this.http.put<ILineaDeInvestigacion>(this.resourceUrl, lineaDeInvestigacion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILineaDeInvestigacion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILineaDeInvestigacion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
