import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductoProyecto } from 'app/shared/model/producto-proyecto.model';
import { ProductoProyectoService } from './producto-proyecto.service';
import { ProductoProyectoComponent } from './producto-proyecto.component';
import { ProductoProyectoDetailComponent } from './producto-proyecto-detail.component';
import { ProductoProyectoUpdateComponent } from './producto-proyecto-update.component';
import { ProductoProyectoDeletePopupComponent } from './producto-proyecto-delete-dialog.component';
import { IProductoProyecto } from 'app/shared/model/producto-proyecto.model';

@Injectable({ providedIn: 'root' })
export class ProductoProyectoResolve implements Resolve<IProductoProyecto> {
  constructor(private service: ProductoProyectoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductoProyecto> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProductoProyecto>) => response.ok),
        map((productoProyecto: HttpResponse<ProductoProyecto>) => productoProyecto.body)
      );
    }
    return of(new ProductoProyecto());
  }
}

export const productoProyectoRoute: Routes = [
  {
    path: '',
    component: ProductoProyectoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.productoProyecto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductoProyectoDetailComponent,
    resolve: {
      productoProyecto: ProductoProyectoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.productoProyecto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductoProyectoUpdateComponent,
    resolve: {
      productoProyecto: ProductoProyectoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.productoProyecto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductoProyectoUpdateComponent,
    resolve: {
      productoProyecto: ProductoProyectoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.productoProyecto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productoProyectoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProductoProyectoDeletePopupComponent,
    resolve: {
      productoProyecto: ProductoProyectoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciecytApp.productoProyecto.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
