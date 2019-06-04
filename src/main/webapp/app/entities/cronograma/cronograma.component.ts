import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICronograma } from 'app/shared/model/cronograma.model';
import { AccountService } from 'app/core';
import { CronogramaService } from './cronograma.service';

@Component({
  selector: 'jhi-cronograma',
  templateUrl: './cronograma.component.html'
})
export class CronogramaComponent implements OnInit, OnDestroy {
  cronogramas: ICronograma[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected cronogramaService: CronogramaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.cronogramaService
      .query()
      .pipe(
        filter((res: HttpResponse<ICronograma[]>) => res.ok),
        map((res: HttpResponse<ICronograma[]>) => res.body)
      )
      .subscribe(
        (res: ICronograma[]) => {
          this.cronogramas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCronogramas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICronograma) {
    return item.id;
  }

  registerChangeInCronogramas() {
    this.eventSubscriber = this.eventManager.subscribe('cronogramaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
