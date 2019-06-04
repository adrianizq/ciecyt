import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Facultad } from 'app/shared/model/facultad.model';
import { FacultadService } from './facultad.service';
import { FacultadComponent } from './facultad.component';
import { FacultadDetailComponent } from './facultad-detail.component';
import { FacultadUpdateComponent } from './facultad-update.component';
import { FacultadDeletePopupComponent } from './facultad-delete-dialog.component';
import { IFacultad } from 'app/shared/model/facultad.model';

@Injectable({ providedIn: 'root' })
export class FacultadResolve implements Resolve<IFacultad> {
  constructor(private service: FacultadService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFacultad> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Facultad>) => response.ok),
        map((facultad: HttpResponse<Facultad>) => facultad.body)
      );
    }
    return of(new Facultad());
  }
}

export const facultadRoute: Routes = [
  {
    path: '',
    component: FacultadComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.facultad.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FacultadDetailComponent,
    resolve: {
      facultad: FacultadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.facultad.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FacultadUpdateComponent,
    resolve: {
      facultad: FacultadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.facultad.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FacultadUpdateComponent,
    resolve: {
      facultad: FacultadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.facultad.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const facultadPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FacultadDeletePopupComponent,
    resolve: {
      facultad: FacultadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.facultad.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
