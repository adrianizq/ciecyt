import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Empresas } from 'app/shared/model/empresas.model';
import { EmpresasService } from './empresas.service';
import { EmpresasComponent } from './empresas.component';
import { EmpresasDetailComponent } from './empresas-detail.component';
import { EmpresasUpdateComponent } from './empresas-update.component';
import { EmpresasDeletePopupComponent } from './empresas-delete-dialog.component';
import { IEmpresas } from 'app/shared/model/empresas.model';

@Injectable({ providedIn: 'root' })
export class EmpresasResolve implements Resolve<IEmpresas> {
  constructor(private service: EmpresasService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEmpresas> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Empresas>) => response.ok),
        map((empresas: HttpResponse<Empresas>) => empresas.body)
      );
    }
    return of(new Empresas());
  }
}

export const empresasRoute: Routes = [
  {
    path: '',
    component: EmpresasComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.empresas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EmpresasDetailComponent,
    resolve: {
      empresas: EmpresasResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.empresas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EmpresasUpdateComponent,
    resolve: {
      empresas: EmpresasResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.empresas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EmpresasUpdateComponent,
    resolve: {
      empresas: EmpresasResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.empresas.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const empresasPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EmpresasDeletePopupComponent,
    resolve: {
      empresas: EmpresasResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.empresas.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
