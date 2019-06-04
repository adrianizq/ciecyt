import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICategorizacion } from 'app/shared/model/categorizacion.model';
import { AccountService } from 'app/core';
import { CategorizacionService } from './categorizacion.service';

@Component({
  selector: 'jhi-categorizacion',
  templateUrl: './categorizacion.component.html'
})
export class CategorizacionComponent implements OnInit, OnDestroy {
  categorizacions: ICategorizacion[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected categorizacionService: CategorizacionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.categorizacionService
      .query()
      .pipe(
        filter((res: HttpResponse<ICategorizacion[]>) => res.ok),
        map((res: HttpResponse<ICategorizacion[]>) => res.body)
      )
      .subscribe(
        (res: ICategorizacion[]) => {
          this.categorizacions = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCategorizacions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICategorizacion) {
    return item.id;
  }

  registerChangeInCategorizacions() {
    this.eventSubscriber = this.eventManager.subscribe('categorizacionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
