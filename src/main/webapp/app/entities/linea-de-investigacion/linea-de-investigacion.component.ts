import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILineaDeInvestigacion } from 'app/shared/model/linea-de-investigacion.model';
import { AccountService } from 'app/core';
import { LineaDeInvestigacionService } from './linea-de-investigacion.service';

@Component({
  selector: 'jhi-linea-de-investigacion',
  templateUrl: './linea-de-investigacion.component.html'
})
export class LineaDeInvestigacionComponent implements OnInit, OnDestroy {
  lineaDeInvestigacions: ILineaDeInvestigacion[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected lineaDeInvestigacionService: LineaDeInvestigacionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.lineaDeInvestigacionService
      .query()
      .pipe(
        filter((res: HttpResponse<ILineaDeInvestigacion[]>) => res.ok),
        map((res: HttpResponse<ILineaDeInvestigacion[]>) => res.body)
      )
      .subscribe(
        (res: ILineaDeInvestigacion[]) => {
          this.lineaDeInvestigacions = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInLineaDeInvestigacions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILineaDeInvestigacion) {
    return item.id;
  }

  registerChangeInLineaDeInvestigacions() {
    this.eventSubscriber = this.eventManager.subscribe('lineaDeInvestigacionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
