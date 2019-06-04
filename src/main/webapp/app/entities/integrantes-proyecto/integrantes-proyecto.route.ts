import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IntegrantesProyecto } from 'app/shared/model/integrantes-proyecto.model';
import { IntegrantesProyectoService } from './integrantes-proyecto.service';
import { IntegrantesProyectoComponent } from './integrantes-proyecto.component';
import { IntegrantesProyectoDetailComponent } from './integrantes-proyecto-detail.component';
import { IntegrantesProyectoUpdateComponent } from './integrantes-proyecto-update.component';
import { IntegrantesProyectoDeletePopupComponent } from './integrantes-proyecto-delete-dialog.component';
import { IIntegrantesProyecto } from 'app/shared/model/integrantes-proyecto.model';

@Injectable({ providedIn: 'root' })
export class IntegrantesProyectoResolve implements Resolve<IIntegrantesProyecto> {
  constructor(private service: IntegrantesProyectoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IIntegrantesProyecto> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<IntegrantesProyecto>) => response.ok),
        map((integrantesProyecto: HttpResponse<IntegrantesProyecto>) => integrantesProyecto.body)
      );
    }
    return of(new IntegrantesProyecto());
  }
}

export const integrantesProyectoRoute: Routes = [
  {
    path: '',
    component: IntegrantesProyectoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.integrantesProyecto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IntegrantesProyectoDetailComponent,
    resolve: {
      integrantesProyecto: IntegrantesProyectoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.integrantesProyecto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IntegrantesProyectoUpdateComponent,
    resolve: {
      integrantesProyecto: IntegrantesProyectoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.integrantesProyecto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IntegrantesProyectoUpdateComponent,
    resolve: {
      integrantesProyecto: IntegrantesProyectoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.integrantesProyecto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const integrantesProyectoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: IntegrantesProyectoDeletePopupComponent,
    resolve: {
      integrantesProyecto: IntegrantesProyectoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.integrantesProyecto.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
