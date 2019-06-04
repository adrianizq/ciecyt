import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EntidadFinanciadora } from 'app/shared/model/entidad-financiadora.model';
import { EntidadFinanciadoraService } from './entidad-financiadora.service';
import { EntidadFinanciadoraComponent } from './entidad-financiadora.component';
import { EntidadFinanciadoraDetailComponent } from './entidad-financiadora-detail.component';
import { EntidadFinanciadoraUpdateComponent } from './entidad-financiadora-update.component';
import { EntidadFinanciadoraDeletePopupComponent } from './entidad-financiadora-delete-dialog.component';
import { IEntidadFinanciadora } from 'app/shared/model/entidad-financiadora.model';

@Injectable({ providedIn: 'root' })
export class EntidadFinanciadoraResolve implements Resolve<IEntidadFinanciadora> {
  constructor(private service: EntidadFinanciadoraService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEntidadFinanciadora> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EntidadFinanciadora>) => response.ok),
        map((entidadFinanciadora: HttpResponse<EntidadFinanciadora>) => entidadFinanciadora.body)
      );
    }
    return of(new EntidadFinanciadora());
  }
}

export const entidadFinanciadoraRoute: Routes = [
  {
    path: '',
    component: EntidadFinanciadoraComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.entidadFinanciadora.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EntidadFinanciadoraDetailComponent,
    resolve: {
      entidadFinanciadora: EntidadFinanciadoraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.entidadFinanciadora.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EntidadFinanciadoraUpdateComponent,
    resolve: {
      entidadFinanciadora: EntidadFinanciadoraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.entidadFinanciadora.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EntidadFinanciadoraUpdateComponent,
    resolve: {
      entidadFinanciadora: EntidadFinanciadoraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.entidadFinanciadora.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const entidadFinanciadoraPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EntidadFinanciadoraDeletePopupComponent,
    resolve: {
      entidadFinanciadora: EntidadFinanciadoraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.entidadFinanciadora.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
