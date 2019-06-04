import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICronograma } from 'app/shared/model/cronograma.model';

type EntityResponseType = HttpResponse<ICronograma>;
type EntityArrayResponseType = HttpResponse<ICronograma[]>;

@Injectable({ providedIn: 'root' })
export class CronogramaService {
  public resourceUrl = SERVER_API_URL + 'api/cronogramas';

  constructor(protected http: HttpClient) {}

  create(cronograma: ICronograma): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cronograma);
    return this.http
      .post<ICronograma>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cronograma: ICronograma): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cronograma);
    return this.http
      .put<ICronograma>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICronograma>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICronograma[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(cronograma: ICronograma): ICronograma {
    const copy: ICronograma = Object.assign({}, cronograma, {
      fechaIni: cronograma.fechaIni != null && cronograma.fechaIni.isValid() ? cronograma.fechaIni.format(DATE_FORMAT) : null,
      fechaFin: cronograma.fechaFin != null && cronograma.fechaFin.isValid() ? cronograma.fechaFin.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaIni = res.body.fechaIni != null ? moment(res.body.fechaIni) : null;
      res.body.fechaFin = res.body.fechaFin != null ? moment(res.body.fechaFin) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((cronograma: ICronograma) => {
        cronograma.fechaIni = cronograma.fechaIni != null ? moment(cronograma.fechaIni) : null;
        cronograma.fechaFin = cronograma.fechaFin != null ? moment(cronograma.fechaFin) : null;
      });
    }
    return res;
  }
}
