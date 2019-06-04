import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProductoProyecto } from 'app/shared/model/producto-proyecto.model';
import { AccountService } from 'app/core';
import { ProductoProyectoService } from './producto-proyecto.service';

@Component({
  selector: 'jhi-producto-proyecto',
  templateUrl: './producto-proyecto.component.html'
})
export class ProductoProyectoComponent implements OnInit, OnDestroy {
  productoProyectos: IProductoProyecto[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected productoProyectoService: ProductoProyectoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.productoProyectoService
      .query()
      .pipe(
        filter((res: HttpResponse<IProductoProyecto[]>) => res.ok),
        map((res: HttpResponse<IProductoProyecto[]>) => res.body)
      )
      .subscribe(
        (res: IProductoProyecto[]) => {
          this.productoProyectos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProductoProyectos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductoProyecto) {
    return item.id;
  }

  registerChangeInProductoProyectos() {
    this.eventSubscriber = this.eventManager.subscribe('productoProyectoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
