import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProducto } from 'app/shared/model/producto.model';
import { AccountService } from 'app/core';
import { ProductoService } from './producto.service';

@Component({
  selector: 'jhi-producto',
  templateUrl: './producto.component.html'
})
export class ProductoComponent implements OnInit, OnDestroy {
  productos: IProducto[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected productoService: ProductoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.productoService
      .query()
      .pipe(
        filter((res: HttpResponse<IProducto[]>) => res.ok),
        map((res: HttpResponse<IProducto[]>) => res.body)
      )
      .subscribe(
        (res: IProducto[]) => {
          this.productos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProductos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProducto) {
    return item.id;
  }

  registerChangeInProductos() {
    this.eventSubscriber = this.eventManager.subscribe('productoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
