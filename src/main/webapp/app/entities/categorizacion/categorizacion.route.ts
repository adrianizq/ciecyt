import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Categorizacion } from 'app/shared/model/categorizacion.model';
import { CategorizacionService } from './categorizacion.service';
import { CategorizacionComponent } from './categorizacion.component';
import { CategorizacionDetailComponent } from './categorizacion-detail.component';
import { CategorizacionUpdateComponent } from './categorizacion-update.component';
import { CategorizacionDeletePopupComponent } from './categorizacion-delete-dialog.component';
import { ICategorizacion } from 'app/shared/model/categorizacion.model';

@Injectable({ providedIn: 'root' })
export class CategorizacionResolve implements Resolve<ICategorizacion> {
  constructor(private service: CategorizacionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICategorizacion> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Categorizacion>) => response.ok),
        map((categorizacion: HttpResponse<Categorizacion>) => categorizacion.body)
      );
    }
    return of(new Categorizacion());
  }
}

export const categorizacionRoute: Routes = [
  {
    path: '',
    component: CategorizacionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.categorizacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CategorizacionDetailComponent,
    resolve: {
      categorizacion: CategorizacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.categorizacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CategorizacionUpdateComponent,
    resolve: {
      categorizacion: CategorizacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.categorizacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CategorizacionUpdateComponent,
    resolve: {
      categorizacion: CategorizacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.categorizacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const categorizacionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CategorizacionDeletePopupComponent,
    resolve: {
      categorizacion: CategorizacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.categorizacion.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
