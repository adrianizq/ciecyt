import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Preguntas } from 'app/shared/model/preguntas.model';
import { PreguntasService } from './preguntas.service';
import { PreguntasComponent } from './preguntas.component';
import { PreguntasDetailComponent } from './preguntas-detail.component';
import { PreguntasUpdateComponent } from './preguntas-update.component';
import { PreguntasDeletePopupComponent } from './preguntas-delete-dialog.component';
import { IPreguntas } from 'app/shared/model/preguntas.model';

@Injectable({ providedIn: 'root' })
export class PreguntasResolve implements Resolve<IPreguntas> {
  constructor(private service: PreguntasService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPreguntas> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Preguntas>) => response.ok),
        map((preguntas: HttpResponse<Preguntas>) => preguntas.body)
      );
    }
    return of(new Preguntas());
  }
}

export const preguntasRoute: Routes = [
  {
    path: '',
    component: PreguntasComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.preguntas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PreguntasDetailComponent,
    resolve: {
      preguntas: PreguntasResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.preguntas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PreguntasUpdateComponent,
    resolve: {
      preguntas: PreguntasResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.preguntas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PreguntasUpdateComponent,
    resolve: {
      preguntas: PreguntasResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.preguntas.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const preguntasPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PreguntasDeletePopupComponent,
    resolve: {
      preguntas: PreguntasResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.preguntas.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
