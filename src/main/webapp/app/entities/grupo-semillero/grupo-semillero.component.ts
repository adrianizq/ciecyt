import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IGrupoSemillero } from 'app/shared/model/grupo-semillero.model';
import { AccountService } from 'app/core';
import { GrupoSemilleroService } from './grupo-semillero.service';

@Component({
  selector: 'jhi-grupo-semillero',
  templateUrl: './grupo-semillero.component.html'
})
export class GrupoSemilleroComponent implements OnInit, OnDestroy {
  grupoSemilleros: IGrupoSemillero[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected grupoSemilleroService: GrupoSemilleroService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.grupoSemilleroService
      .query()
      .pipe(
        filter((res: HttpResponse<IGrupoSemillero[]>) => res.ok),
        map((res: HttpResponse<IGrupoSemillero[]>) => res.body)
      )
      .subscribe(
        (res: IGrupoSemillero[]) => {
          this.grupoSemilleros = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInGrupoSemilleros();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IGrupoSemillero) {
    return item.id;
  }

  registerChangeInGrupoSemilleros() {
    this.eventSubscriber = this.eventManager.subscribe('grupoSemilleroListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
