import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PresupuestoValor } from 'app/shared/model/presupuesto-valor.model';
import { PresupuestoValorService } from './presupuesto-valor.service';
import { PresupuestoValorComponent } from './presupuesto-valor.component';
import { PresupuestoValorDetailComponent } from './presupuesto-valor-detail.component';
import { PresupuestoValorUpdateComponent } from './presupuesto-valor-update.component';
import { PresupuestoValorDeletePopupComponent } from './presupuesto-valor-delete-dialog.component';
import { IPresupuestoValor } from 'app/shared/model/presupuesto-valor.model';

@Injectable({ providedIn: 'root' })
export class PresupuestoValorResolve implements Resolve<IPresupuestoValor> {
  constructor(private service: PresupuestoValorService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPresupuestoValor> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PresupuestoValor>) => response.ok),
        map((presupuestoValor: HttpResponse<PresupuestoValor>) => presupuestoValor.body)
      );
    }
    return of(new PresupuestoValor());
  }
}

export const presupuestoValorRoute: Routes = [
  {
    path: '',
    component: PresupuestoValorComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.presupuestoValor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PresupuestoValorDetailComponent,
    resolve: {
      presupuestoValor: PresupuestoValorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.presupuestoValor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PresupuestoValorUpdateComponent,
    resolve: {
      presupuestoValor: PresupuestoValorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.presupuestoValor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PresupuestoValorUpdateComponent,
    resolve: {
      presupuestoValor: PresupuestoValorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.presupuestoValor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const presupuestoValorPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PresupuestoValorDeletePopupComponent,
    resolve: {
      presupuestoValor: PresupuestoValorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.presupuestoValor.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
