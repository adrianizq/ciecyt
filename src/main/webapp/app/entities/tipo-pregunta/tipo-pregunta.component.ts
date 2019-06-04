import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITipoPregunta } from 'app/shared/model/tipo-pregunta.model';
import { AccountService } from 'app/core';
import { TipoPreguntaService } from './tipo-pregunta.service';

@Component({
  selector: 'jhi-tipo-pregunta',
  templateUrl: './tipo-pregunta.component.html'
})
export class TipoPreguntaComponent implements OnInit, OnDestroy {
  tipoPreguntas: ITipoPregunta[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected tipoPreguntaService: TipoPreguntaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.tipoPreguntaService
      .query()
      .pipe(
        filter((res: HttpResponse<ITipoPregunta[]>) => res.ok),
        map((res: HttpResponse<ITipoPregunta[]>) => res.body)
      )
      .subscribe(
        (res: ITipoPregunta[]) => {
          this.tipoPreguntas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTipoPreguntas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITipoPregunta) {
    return item.id;
  }

  registerChangeInTipoPreguntas() {
    this.eventSubscriber = this.eventManager.subscribe('tipoPreguntaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
