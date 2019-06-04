import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Cronograma } from 'app/shared/model/cronograma.model';
import { CronogramaService } from './cronograma.service';
import { CronogramaComponent } from './cronograma.component';
import { CronogramaDetailComponent } from './cronograma-detail.component';
import { CronogramaUpdateComponent } from './cronograma-update.component';
import { CronogramaDeletePopupComponent } from './cronograma-delete-dialog.component';
import { ICronograma } from 'app/shared/model/cronograma.model';

@Injectable({ providedIn: 'root' })
export class CronogramaResolve implements Resolve<ICronograma> {
  constructor(private service: CronogramaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICronograma> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Cronograma>) => response.ok),
        map((cronograma: HttpResponse<Cronograma>) => cronograma.body)
      );
    }
    return of(new Cronograma());
  }
}

export const cronogramaRoute: Routes = [
  {
    path: '',
    component: CronogramaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.cronograma.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CronogramaDetailComponent,
    resolve: {
      cronograma: CronogramaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.cronograma.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CronogramaUpdateComponent,
    resolve: {
      cronograma: CronogramaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.cronograma.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CronogramaUpdateComponent,
    resolve: {
      cronograma: CronogramaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.cronograma.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const cronogramaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CronogramaDeletePopupComponent,
    resolve: {
      cronograma: CronogramaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.cronograma.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
