import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Proyecto } from 'app/shared/model/proyecto.model';
import { ProyectoService } from './proyecto.service';
import { ProyectoComponent } from './proyecto.component';
import { ProyectoDetailComponent } from './proyecto-detail.component';
import { ProyectoUpdateComponent } from './proyecto-update.component';
import { ProyectoDeletePopupComponent } from './proyecto-delete-dialog.component';
import { IProyecto } from 'app/shared/model/proyecto.model';

@Injectable({ providedIn: 'root' })
export class ProyectoResolve implements Resolve<IProyecto> {
  constructor(private service: ProyectoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProyecto> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Proyecto>) => response.ok),
        map((proyecto: HttpResponse<Proyecto>) => proyecto.body)
      );
    }
    return of(new Proyecto());
  }
}

export const proyectoRoute: Routes = [
  {
    path: '',
    component: ProyectoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ciecytApp.proyecto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProyectoDetailComponent,
    resolve: {
      proyecto: ProyectoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.proyecto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProyectoUpdateComponent,
    resolve: {
      proyecto: ProyectoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.proyecto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProyectoUpdateComponent,
    resolve: {
      proyecto: ProyectoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.proyecto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const proyectoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProyectoDeletePopupComponent,
    resolve: {
      proyecto: ProyectoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.proyecto.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
