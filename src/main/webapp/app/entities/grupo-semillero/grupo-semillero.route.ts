import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { GrupoSemillero } from 'app/shared/model/grupo-semillero.model';
import { GrupoSemilleroService } from './grupo-semillero.service';
import { GrupoSemilleroComponent } from './grupo-semillero.component';
import { GrupoSemilleroDetailComponent } from './grupo-semillero-detail.component';
import { GrupoSemilleroUpdateComponent } from './grupo-semillero-update.component';
import { GrupoSemilleroDeletePopupComponent } from './grupo-semillero-delete-dialog.component';
import { IGrupoSemillero } from 'app/shared/model/grupo-semillero.model';

@Injectable({ providedIn: 'root' })
export class GrupoSemilleroResolve implements Resolve<IGrupoSemillero> {
  constructor(private service: GrupoSemilleroService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IGrupoSemillero> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<GrupoSemillero>) => response.ok),
        map((grupoSemillero: HttpResponse<GrupoSemillero>) => grupoSemillero.body)
      );
    }
    return of(new GrupoSemillero());
  }
}

export const grupoSemilleroRoute: Routes = [
  {
    path: '',
    component: GrupoSemilleroComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.grupoSemillero.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GrupoSemilleroDetailComponent,
    resolve: {
      grupoSemillero: GrupoSemilleroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.grupoSemillero.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GrupoSemilleroUpdateComponent,
    resolve: {
      grupoSemillero: GrupoSemilleroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.grupoSemillero.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GrupoSemilleroUpdateComponent,
    resolve: {
      grupoSemillero: GrupoSemilleroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.grupoSemillero.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const grupoSemilleroPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: GrupoSemilleroDeletePopupComponent,
    resolve: {
      grupoSemillero: GrupoSemilleroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.grupoSemillero.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
