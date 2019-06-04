import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PreguntaRespuesta } from 'app/shared/model/pregunta-respuesta.model';
import { PreguntaRespuestaService } from './pregunta-respuesta.service';
import { PreguntaRespuestaComponent } from './pregunta-respuesta.component';
import { PreguntaRespuestaDetailComponent } from './pregunta-respuesta-detail.component';
import { PreguntaRespuestaUpdateComponent } from './pregunta-respuesta-update.component';
import { PreguntaRespuestaDeletePopupComponent } from './pregunta-respuesta-delete-dialog.component';
import { IPreguntaRespuesta } from 'app/shared/model/pregunta-respuesta.model';

@Injectable({ providedIn: 'root' })
export class PreguntaRespuestaResolve implements Resolve<IPreguntaRespuesta> {
  constructor(private service: PreguntaRespuestaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPreguntaRespuesta> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PreguntaRespuesta>) => response.ok),
        map((preguntaRespuesta: HttpResponse<PreguntaRespuesta>) => preguntaRespuesta.body)
      );
    }
    return of(new PreguntaRespuesta());
  }
}

export const preguntaRespuestaRoute: Routes = [
  {
    path: '',
    component: PreguntaRespuestaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.preguntaRespuesta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PreguntaRespuestaDetailComponent,
    resolve: {
      preguntaRespuesta: PreguntaRespuestaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.preguntaRespuesta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PreguntaRespuestaUpdateComponent,
    resolve: {
      preguntaRespuesta: PreguntaRespuestaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.preguntaRespuesta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PreguntaRespuestaUpdateComponent,
    resolve: {
      preguntaRespuesta: PreguntaRespuestaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.preguntaRespuesta.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const preguntaRespuestaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PreguntaRespuestaDeletePopupComponent,
    resolve: {
      preguntaRespuesta: PreguntaRespuestaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.preguntaRespuesta.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
