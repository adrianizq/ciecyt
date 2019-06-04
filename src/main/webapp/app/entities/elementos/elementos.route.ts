import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Elementos } from 'app/shared/model/elementos.model';
import { ElementosService } from './elementos.service';
import { ElementosComponent } from './elementos.component';
import { ElementosDetailComponent } from './elementos-detail.component';
import { ElementosUpdateComponent } from './elementos-update.component';
import { ElementosDeletePopupComponent } from './elementos-delete-dialog.component';
import { IElementos } from 'app/shared/model/elementos.model';

@Injectable({ providedIn: 'root' })
export class ElementosResolve implements Resolve<IElementos> {
  constructor(private service: ElementosService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IElementos> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Elementos>) => response.ok),
        map((elementos: HttpResponse<Elementos>) => elementos.body)
      );
    }
    return of(new Elementos());
  }
}

export const elementosRoute: Routes = [
  {
    path: '',
    component: ElementosComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.elementos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ElementosDetailComponent,
    resolve: {
      elementos: ElementosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.elementos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ElementosUpdateComponent,
    resolve: {
      elementos: ElementosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.elementos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ElementosUpdateComponent,
    resolve: {
      elementos: ElementosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.elementos.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const elementosPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ElementosDeletePopupComponent,
    resolve: {
      elementos: ElementosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.elementos.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
