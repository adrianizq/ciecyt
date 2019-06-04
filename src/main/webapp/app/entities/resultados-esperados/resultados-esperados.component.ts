import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IResultadosEsperados } from 'app/shared/model/resultados-esperados.model';
import { AccountService } from 'app/core';
import { ResultadosEsperadosService } from './resultados-esperados.service';

@Component({
  selector: 'jhi-resultados-esperados',
  templateUrl: './resultados-esperados.component.html'
})
export class ResultadosEsperadosComponent implements OnInit, OnDestroy {
  resultadosEsperados: IResultadosEsperados[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected resultadosEsperadosService: ResultadosEsperadosService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.resultadosEsperadosService
      .query()
      .pipe(
        filter((res: HttpResponse<IResultadosEsperados[]>) => res.ok),
        map((res: HttpResponse<IResultadosEsperados[]>) => res.body)
      )
      .subscribe(
        (res: IResultadosEsperados[]) => {
          this.resultadosEsperados = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInResultadosEsperados();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IResultadosEsperados) {
    return item.id;
  }

  registerChangeInResultadosEsperados() {
    this.eventSubscriber = this.eventManager.subscribe('resultadosEsperadosListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
