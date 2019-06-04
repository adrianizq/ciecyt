import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ImpactosEsperados } from 'app/shared/model/impactos-esperados.model';
import { ImpactosEsperadosService } from './impactos-esperados.service';
import { ImpactosEsperadosComponent } from './impactos-esperados.component';
import { ImpactosEsperadosDetailComponent } from './impactos-esperados-detail.component';
import { ImpactosEsperadosUpdateComponent } from './impactos-esperados-update.component';
import { ImpactosEsperadosDeletePopupComponent } from './impactos-esperados-delete-dialog.component';
import { IImpactosEsperados } from 'app/shared/model/impactos-esperados.model';

@Injectable({ providedIn: 'root' })
export class ImpactosEsperadosResolve implements Resolve<IImpactosEsperados> {
  constructor(private service: ImpactosEsperadosService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IImpactosEsperados> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ImpactosEsperados>) => response.ok),
        map((impactosEsperados: HttpResponse<ImpactosEsperados>) => impactosEsperados.body)
      );
    }
    return of(new ImpactosEsperados());
  }
}

export const impactosEsperadosRoute: Routes = [
  {
    path: '',
    component: ImpactosEsperadosComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.impactosEsperados.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ImpactosEsperadosDetailComponent,
    resolve: {
      impactosEsperados: ImpactosEsperadosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.impactosEsperados.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ImpactosEsperadosUpdateComponent,
    resolve: {
      impactosEsperados: ImpactosEsperadosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.impactosEsperados.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ImpactosEsperadosUpdateComponent,
    resolve: {
      impactosEsperados: ImpactosEsperadosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.impactosEsperados.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const impactosEsperadosPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ImpactosEsperadosDeletePopupComponent,
    resolve: {
      impactosEsperados: ImpactosEsperadosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.impactosEsperados.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
