import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ElementosProyecto } from 'app/shared/model/elementos-proyecto.model';
import { ElementosProyectoService } from './elementos-proyecto.service';
import { ElementosProyectoComponent } from './elementos-proyecto.component';
import { ElementosProyectoDetailComponent } from './elementos-proyecto-detail.component';
import { ElementosProyectoUpdateComponent } from './elementos-proyecto-update.component';
import { ElementosProyectoDeletePopupComponent } from './elementos-proyecto-delete-dialog.component';
import { IElementosProyecto } from 'app/shared/model/elementos-proyecto.model';

@Injectable({ providedIn: 'root' })
export class ElementosProyectoResolve implements Resolve<IElementosProyecto> {
  constructor(private service: ElementosProyectoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IElementosProyecto> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ElementosProyecto>) => response.ok),
        map((elementosProyecto: HttpResponse<ElementosProyecto>) => elementosProyecto.body)
      );
    }
    return of(new ElementosProyecto());
  }
}

export const elementosProyectoRoute: Routes = [
  {
    path: '',
    component: ElementosProyectoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.elementosProyecto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ElementosProyectoDetailComponent,
    resolve: {
      elementosProyecto: ElementosProyectoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.elementosProyecto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ElementosProyectoUpdateComponent,
    resolve: {
      elementosProyecto: ElementosProyectoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.elementosProyecto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ElementosProyectoUpdateComponent,
    resolve: {
      elementosProyecto: ElementosProyectoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.elementosProyecto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const elementosProyectoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ElementosProyectoDeletePopupComponent,
    resolve: {
      elementosProyecto: ElementosProyectoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.elementosProyecto.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
