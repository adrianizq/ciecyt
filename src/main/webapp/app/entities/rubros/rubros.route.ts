import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Rubros } from 'app/shared/model/rubros.model';
import { RubrosService } from './rubros.service';
import { RubrosComponent } from './rubros.component';
import { RubrosDetailComponent } from './rubros-detail.component';
import { RubrosUpdateComponent } from './rubros-update.component';
import { RubrosDeletePopupComponent } from './rubros-delete-dialog.component';
import { IRubros } from 'app/shared/model/rubros.model';

@Injectable({ providedIn: 'root' })
export class RubrosResolve implements Resolve<IRubros> {
  constructor(private service: RubrosService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRubros> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Rubros>) => response.ok),
        map((rubros: HttpResponse<Rubros>) => rubros.body)
      );
    }
    return of(new Rubros());
  }
}

export const rubrosRoute: Routes = [
  {
    path: '',
    component: RubrosComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.rubros.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RubrosDetailComponent,
    resolve: {
      rubros: RubrosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.rubros.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RubrosUpdateComponent,
    resolve: {
      rubros: RubrosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.rubros.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RubrosUpdateComponent,
    resolve: {
      rubros: RubrosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.rubros.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const rubrosPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RubrosDeletePopupComponent,
    resolve: {
      rubros: RubrosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.rubros.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
