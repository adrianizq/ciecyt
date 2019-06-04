import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RolesUsuarioProyecto } from 'app/shared/model/roles-usuario-proyecto.model';
import { RolesUsuarioProyectoService } from './roles-usuario-proyecto.service';
import { RolesUsuarioProyectoComponent } from './roles-usuario-proyecto.component';
import { RolesUsuarioProyectoDetailComponent } from './roles-usuario-proyecto-detail.component';
import { RolesUsuarioProyectoUpdateComponent } from './roles-usuario-proyecto-update.component';
import { RolesUsuarioProyectoDeletePopupComponent } from './roles-usuario-proyecto-delete-dialog.component';
import { IRolesUsuarioProyecto } from 'app/shared/model/roles-usuario-proyecto.model';

@Injectable({ providedIn: 'root' })
export class RolesUsuarioProyectoResolve implements Resolve<IRolesUsuarioProyecto> {
  constructor(private service: RolesUsuarioProyectoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRolesUsuarioProyecto> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<RolesUsuarioProyecto>) => response.ok),
        map((rolesUsuarioProyecto: HttpResponse<RolesUsuarioProyecto>) => rolesUsuarioProyecto.body)
      );
    }
    return of(new RolesUsuarioProyecto());
  }
}

export const rolesUsuarioProyectoRoute: Routes = [
  {
    path: '',
    component: RolesUsuarioProyectoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.rolesUsuarioProyecto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RolesUsuarioProyectoDetailComponent,
    resolve: {
      rolesUsuarioProyecto: RolesUsuarioProyectoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.rolesUsuarioProyecto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RolesUsuarioProyectoUpdateComponent,
    resolve: {
      rolesUsuarioProyecto: RolesUsuarioProyectoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.rolesUsuarioProyecto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RolesUsuarioProyectoUpdateComponent,
    resolve: {
      rolesUsuarioProyecto: RolesUsuarioProyectoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.rolesUsuarioProyecto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const rolesUsuarioProyectoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RolesUsuarioProyectoDeletePopupComponent,
    resolve: {
      rolesUsuarioProyecto: RolesUsuarioProyectoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.rolesUsuarioProyecto.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
