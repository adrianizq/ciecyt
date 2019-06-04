import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEntidadFinanciadora } from 'app/shared/model/entidad-financiadora.model';
import { AccountService } from 'app/core';
import { EntidadFinanciadoraService } from './entidad-financiadora.service';

@Component({
  selector: 'jhi-entidad-financiadora',
  templateUrl: './entidad-financiadora.component.html'
})
export class EntidadFinanciadoraComponent implements OnInit, OnDestroy {
  entidadFinanciadoras: IEntidadFinanciadora[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected entidadFinanciadoraService: EntidadFinanciadoraService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.entidadFinanciadoraService
      .query()
      .pipe(
        filter((res: HttpResponse<IEntidadFinanciadora[]>) => res.ok),
        map((res: HttpResponse<IEntidadFinanciadora[]>) => res.body)
      )
      .subscribe(
        (res: IEntidadFinanciadora[]) => {
          this.entidadFinanciadoras = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInEntidadFinanciadoras();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEntidadFinanciadora) {
    return item.id;
  }

  registerChangeInEntidadFinanciadoras() {
    this.eventSubscriber = this.eventManager.subscribe('entidadFinanciadoraListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
