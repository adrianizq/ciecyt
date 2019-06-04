import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRubros } from 'app/shared/model/rubros.model';
import { AccountService } from 'app/core';
import { RubrosService } from './rubros.service';

@Component({
  selector: 'jhi-rubros',
  templateUrl: './rubros.component.html'
})
export class RubrosComponent implements OnInit, OnDestroy {
  rubros: IRubros[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected rubrosService: RubrosService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.rubrosService
      .query()
      .pipe(
        filter((res: HttpResponse<IRubros[]>) => res.ok),
        map((res: HttpResponse<IRubros[]>) => res.body)
      )
      .subscribe(
        (res: IRubros[]) => {
          this.rubros = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInRubros();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRubros) {
    return item.id;
  }

  registerChangeInRubros() {
    this.eventSubscriber = this.eventManager.subscribe('rubrosListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
