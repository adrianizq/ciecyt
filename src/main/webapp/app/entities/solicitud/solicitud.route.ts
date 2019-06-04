import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Solicitud } from 'app/shared/model/solicitud.model';
import { SolicitudService } from './solicitud.service';
import { SolicitudComponent } from './solicitud.component';
import { SolicitudDetailComponent } from './solicitud-detail.component';
import { SolicitudUpdateComponent } from './solicitud-update.component';
import { SolicitudDeletePopupComponent } from './solicitud-delete-dialog.component';
import { ISolicitud } from 'app/shared/model/solicitud.model';

@Injectable({ providedIn: 'root' })
export class SolicitudResolve implements Resolve<ISolicitud> {
  constructor(private service: SolicitudService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISolicitud> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Solicitud>) => response.ok),
        map((solicitud: HttpResponse<Solicitud>) => solicitud.body)
      );
    }
    return of(new Solicitud());
  }
}

export const solicitudRoute: Routes = [
  {
    path: '',
    component: SolicitudComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.solicitud.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SolicitudDetailComponent,
    resolve: {
      solicitud: SolicitudResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.solicitud.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SolicitudUpdateComponent,
    resolve: {
      solicitud: SolicitudResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.solicitud.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SolicitudUpdateComponent,
    resolve: {
      solicitud: SolicitudResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.solicitud.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const solicitudPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SolicitudDeletePopupComponent,
    resolve: {
      solicitud: SolicitudResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.solicitud.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
