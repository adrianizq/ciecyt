import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FichaTecnica } from 'app/shared/model/ficha-tecnica.model';
import { FichaTecnicaService } from './ficha-tecnica.service';
import { FichaTecnicaComponent } from './ficha-tecnica.component';
import { FichaTecnicaDetailComponent } from './ficha-tecnica-detail.component';
import { FichaTecnicaUpdateComponent } from './ficha-tecnica-update.component';
import { FichaTecnicaDeletePopupComponent } from './ficha-tecnica-delete-dialog.component';
import { IFichaTecnica } from 'app/shared/model/ficha-tecnica.model';

@Injectable({ providedIn: 'root' })
export class FichaTecnicaResolve implements Resolve<IFichaTecnica> {
  constructor(private service: FichaTecnicaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFichaTecnica> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FichaTecnica>) => response.ok),
        map((fichaTecnica: HttpResponse<FichaTecnica>) => fichaTecnica.body)
      );
    }
    return of(new FichaTecnica());
  }
}

export const fichaTecnicaRoute: Routes = [
  {
    path: '',
    component: FichaTecnicaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.fichaTecnica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FichaTecnicaDetailComponent,
    resolve: {
      fichaTecnica: FichaTecnicaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.fichaTecnica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FichaTecnicaUpdateComponent,
    resolve: {
      fichaTecnica: FichaTecnicaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.fichaTecnica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FichaTecnicaUpdateComponent,
    resolve: {
      fichaTecnica: FichaTecnicaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.fichaTecnica.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const fichaTecnicaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FichaTecnicaDeletePopupComponent,
    resolve: {
      fichaTecnica: FichaTecnicaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.fichaTecnica.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
