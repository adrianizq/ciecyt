import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ResultadosEsperados } from 'app/shared/model/resultados-esperados.model';
import { ResultadosEsperadosService } from './resultados-esperados.service';
import { ResultadosEsperadosComponent } from './resultados-esperados.component';
import { ResultadosEsperadosDetailComponent } from './resultados-esperados-detail.component';
import { ResultadosEsperadosUpdateComponent } from './resultados-esperados-update.component';
import { ResultadosEsperadosDeletePopupComponent } from './resultados-esperados-delete-dialog.component';
import { IResultadosEsperados } from 'app/shared/model/resultados-esperados.model';

@Injectable({ providedIn: 'root' })
export class ResultadosEsperadosResolve implements Resolve<IResultadosEsperados> {
  constructor(private service: ResultadosEsperadosService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IResultadosEsperados> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ResultadosEsperados>) => response.ok),
        map((resultadosEsperados: HttpResponse<ResultadosEsperados>) => resultadosEsperados.body)
      );
    }
    return of(new ResultadosEsperados());
  }
}

export const resultadosEsperadosRoute: Routes = [
  {
    path: '',
    component: ResultadosEsperadosComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.resultadosEsperados.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ResultadosEsperadosDetailComponent,
    resolve: {
      resultadosEsperados: ResultadosEsperadosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.resultadosEsperados.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ResultadosEsperadosUpdateComponent,
    resolve: {
      resultadosEsperados: ResultadosEsperadosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.resultadosEsperados.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ResultadosEsperadosUpdateComponent,
    resolve: {
      resultadosEsperados: ResultadosEsperadosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.resultadosEsperados.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const resultadosEsperadosPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ResultadosEsperadosDeletePopupComponent,
    resolve: {
      resultadosEsperados: ResultadosEsperadosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.resultadosEsperados.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
