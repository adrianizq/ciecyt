import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISolicitud } from 'app/shared/model/solicitud.model';

type EntityResponseType = HttpResponse<ISolicitud>;
type EntityArrayResponseType = HttpResponse<ISolicitud[]>;

@Injectable({ providedIn: 'root' })
export class SolicitudService {
  public resourceUrl = SERVER_API_URL + 'api/solicituds';

  constructor(protected http: HttpClient) {}

  create(solicitud: ISolicitud): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(solicitud);
    return this.http
      .post<ISolicitud>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(solicitud: ISolicitud): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(solicitud);
    return this.http
      .put<ISolicitud>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISolicitud>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISolicitud[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(solicitud: ISolicitud): ISolicitud {
    const copy: ISolicitud = Object.assign({}, solicitud, {
      fechaCreancion:
        solicitud.fechaCreancion != null && solicitud.fechaCreancion.isValid() ? solicitud.fechaCreancion.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaCreancion = res.body.fechaCreancion != null ? moment(res.body.fechaCreancion) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((solicitud: ISolicitud) => {
        solicitud.fechaCreancion = solicitud.fechaCreancion != null ? moment(solicitud.fechaCreancion) : null;
      });
    }
    return res;
  }
}
