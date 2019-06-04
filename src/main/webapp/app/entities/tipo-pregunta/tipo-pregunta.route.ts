import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TipoPregunta } from 'app/shared/model/tipo-pregunta.model';
import { TipoPreguntaService } from './tipo-pregunta.service';
import { TipoPreguntaComponent } from './tipo-pregunta.component';
import { TipoPreguntaDetailComponent } from './tipo-pregunta-detail.component';
import { TipoPreguntaUpdateComponent } from './tipo-pregunta-update.component';
import { TipoPreguntaDeletePopupComponent } from './tipo-pregunta-delete-dialog.component';
import { ITipoPregunta } from 'app/shared/model/tipo-pregunta.model';

@Injectable({ providedIn: 'root' })
export class TipoPreguntaResolve implements Resolve<ITipoPregunta> {
  constructor(private service: TipoPreguntaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITipoPregunta> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TipoPregunta>) => response.ok),
        map((tipoPregunta: HttpResponse<TipoPregunta>) => tipoPregunta.body)
      );
    }
    return of(new TipoPregunta());
  }
}

export const tipoPreguntaRoute: Routes = [
  {
    path: '',
    component: TipoPreguntaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.tipoPregunta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoPreguntaDetailComponent,
    resolve: {
      tipoPregunta: TipoPreguntaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.tipoPregunta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoPreguntaUpdateComponent,
    resolve: {
      tipoPregunta: TipoPreguntaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.tipoPregunta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoPreguntaUpdateComponent,
    resolve: {
      tipoPregunta: TipoPreguntaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.tipoPregunta.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const tipoPreguntaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TipoPreguntaDeletePopupComponent,
    resolve: {
      tipoPregunta: TipoPreguntaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.tipoPregunta.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
