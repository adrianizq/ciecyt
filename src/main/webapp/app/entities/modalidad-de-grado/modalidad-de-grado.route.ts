import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ModalidadDeGrado } from 'app/shared/model/modalidad-de-grado.model';
import { ModalidadDeGradoService } from './modalidad-de-grado.service';
import { ModalidadDeGradoComponent } from './modalidad-de-grado.component';
import { ModalidadDeGradoDetailComponent } from './modalidad-de-grado-detail.component';
import { ModalidadDeGradoUpdateComponent } from './modalidad-de-grado-update.component';
import { ModalidadDeGradoDeletePopupComponent } from './modalidad-de-grado-delete-dialog.component';
import { IModalidadDeGrado } from 'app/shared/model/modalidad-de-grado.model';

@Injectable({ providedIn: 'root' })
export class ModalidadDeGradoResolve implements Resolve<IModalidadDeGrado> {
  constructor(private service: ModalidadDeGradoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IModalidadDeGrado> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ModalidadDeGrado>) => response.ok),
        map((modalidadDeGrado: HttpResponse<ModalidadDeGrado>) => modalidadDeGrado.body)
      );
    }
    return of(new ModalidadDeGrado());
  }
}

export const modalidadDeGradoRoute: Routes = [
  {
    path: '',
    component: ModalidadDeGradoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.modalidadDeGrado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ModalidadDeGradoDetailComponent,
    resolve: {
      modalidadDeGrado: ModalidadDeGradoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.modalidadDeGrado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ModalidadDeGradoUpdateComponent,
    resolve: {
      modalidadDeGrado: ModalidadDeGradoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.modalidadDeGrado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ModalidadDeGradoUpdateComponent,
    resolve: {
      modalidadDeGrado: ModalidadDeGradoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.modalidadDeGrado.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const modalidadDeGradoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ModalidadDeGradoDeletePopupComponent,
    resolve: {
      modalidadDeGrado: ModalidadDeGradoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.modalidadDeGrado.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
