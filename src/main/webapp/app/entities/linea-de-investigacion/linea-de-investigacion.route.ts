import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LineaDeInvestigacion } from 'app/shared/model/linea-de-investigacion.model';
import { LineaDeInvestigacionService } from './linea-de-investigacion.service';
import { LineaDeInvestigacionComponent } from './linea-de-investigacion.component';
import { LineaDeInvestigacionDetailComponent } from './linea-de-investigacion-detail.component';
import { LineaDeInvestigacionUpdateComponent } from './linea-de-investigacion-update.component';
import { LineaDeInvestigacionDeletePopupComponent } from './linea-de-investigacion-delete-dialog.component';
import { ILineaDeInvestigacion } from 'app/shared/model/linea-de-investigacion.model';

@Injectable({ providedIn: 'root' })
export class LineaDeInvestigacionResolve implements Resolve<ILineaDeInvestigacion> {
  constructor(private service: LineaDeInvestigacionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILineaDeInvestigacion> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<LineaDeInvestigacion>) => response.ok),
        map((lineaDeInvestigacion: HttpResponse<LineaDeInvestigacion>) => lineaDeInvestigacion.body)
      );
    }
    return of(new LineaDeInvestigacion());
  }
}

export const lineaDeInvestigacionRoute: Routes = [
  {
    path: '',
    component: LineaDeInvestigacionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.lineaDeInvestigacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LineaDeInvestigacionDetailComponent,
    resolve: {
      lineaDeInvestigacion: LineaDeInvestigacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.lineaDeInvestigacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LineaDeInvestigacionUpdateComponent,
    resolve: {
      lineaDeInvestigacion: LineaDeInvestigacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.lineaDeInvestigacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LineaDeInvestigacionUpdateComponent,
    resolve: {
      lineaDeInvestigacion: LineaDeInvestigacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.lineaDeInvestigacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const lineaDeInvestigacionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LineaDeInvestigacionDeletePopupComponent,
    resolve: {
      lineaDeInvestigacion: LineaDeInvestigacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.lineaDeInvestigacion.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
