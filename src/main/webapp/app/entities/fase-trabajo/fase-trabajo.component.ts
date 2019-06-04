import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFaseTrabajo } from 'app/shared/model/fase-trabajo.model';
import { AccountService } from 'app/core';
import { FaseTrabajoService } from './fase-trabajo.service';

@Component({
  selector: 'jhi-fase-trabajo',
  templateUrl: './fase-trabajo.component.html'
})
export class FaseTrabajoComponent implements OnInit, OnDestroy {
  faseTrabajos: IFaseTrabajo[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected faseTrabajoService: FaseTrabajoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.faseTrabajoService
      .query()
      .pipe(
        filter((res: HttpResponse<IFaseTrabajo[]>) => res.ok),
        map((res: HttpResponse<IFaseTrabajo[]>) => res.body)
      )
      .subscribe(
        (res: IFaseTrabajo[]) => {
          this.faseTrabajos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInFaseTrabajos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFaseTrabajo) {
    return item.id;
  }

  registerChangeInFaseTrabajos() {
    this.eventSubscriber = this.eventManager.subscribe('faseTrabajoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
