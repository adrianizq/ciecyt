import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPresupuestoValor } from 'app/shared/model/presupuesto-valor.model';
import { AccountService } from 'app/core';
import { PresupuestoValorService } from './presupuesto-valor.service';

@Component({
  selector: 'jhi-presupuesto-valor',
  templateUrl: './presupuesto-valor.component.html'
})
export class PresupuestoValorComponent implements OnInit, OnDestroy {
  presupuestoValors: IPresupuestoValor[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected presupuestoValorService: PresupuestoValorService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.presupuestoValorService
      .query()
      .pipe(
        filter((res: HttpResponse<IPresupuestoValor[]>) => res.ok),
        map((res: HttpResponse<IPresupuestoValor[]>) => res.body)
      )
      .subscribe(
        (res: IPresupuestoValor[]) => {
          this.presupuestoValors = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPresupuestoValors();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPresupuestoValor) {
    return item.id;
  }

  registerChangeInPresupuestoValors() {
    this.eventSubscriber = this.eventManager.subscribe('presupuestoValorListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
