import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IImpactosEsperados } from 'app/shared/model/impactos-esperados.model';
import { AccountService } from 'app/core';
import { ImpactosEsperadosService } from './impactos-esperados.service';

@Component({
  selector: 'jhi-impactos-esperados',
  templateUrl: './impactos-esperados.component.html'
})
export class ImpactosEsperadosComponent implements OnInit, OnDestroy {
  impactosEsperados: IImpactosEsperados[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected impactosEsperadosService: ImpactosEsperadosService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.impactosEsperadosService
      .query()
      .pipe(
        filter((res: HttpResponse<IImpactosEsperados[]>) => res.ok),
        map((res: HttpResponse<IImpactosEsperados[]>) => res.body)
      )
      .subscribe(
        (res: IImpactosEsperados[]) => {
          this.impactosEsperados = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInImpactosEsperados();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IImpactosEsperados) {
    return item.id;
  }

  registerChangeInImpactosEsperados() {
    this.eventSubscriber = this.eventManager.subscribe('impactosEsperadosListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
