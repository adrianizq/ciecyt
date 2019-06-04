import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FaseTrabajo } from 'app/shared/model/fase-trabajo.model';
import { FaseTrabajoService } from './fase-trabajo.service';
import { FaseTrabajoComponent } from './fase-trabajo.component';
import { FaseTrabajoDetailComponent } from './fase-trabajo-detail.component';
import { FaseTrabajoUpdateComponent } from './fase-trabajo-update.component';
import { FaseTrabajoDeletePopupComponent } from './fase-trabajo-delete-dialog.component';
import { IFaseTrabajo } from 'app/shared/model/fase-trabajo.model';

@Injectable({ providedIn: 'root' })
export class FaseTrabajoResolve implements Resolve<IFaseTrabajo> {
  constructor(private service: FaseTrabajoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFaseTrabajo> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FaseTrabajo>) => response.ok),
        map((faseTrabajo: HttpResponse<FaseTrabajo>) => faseTrabajo.body)
      );
    }
    return of(new FaseTrabajo());
  }
}

export const faseTrabajoRoute: Routes = [
  {
    path: '',
    component: FaseTrabajoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.faseTrabajo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FaseTrabajoDetailComponent,
    resolve: {
      faseTrabajo: FaseTrabajoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.faseTrabajo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FaseTrabajoUpdateComponent,
    resolve: {
      faseTrabajo: FaseTrabajoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.faseTrabajo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FaseTrabajoUpdateComponent,
    resolve: {
      faseTrabajo: FaseTrabajoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.faseTrabajo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const faseTrabajoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FaseTrabajoDeletePopupComponent,
    resolve: {
      faseTrabajo: FaseTrabajoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.faseTrabajo.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
