import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPreguntaRespuesta } from 'app/shared/model/pregunta-respuesta.model';
import { AccountService } from 'app/core';
import { PreguntaRespuestaService } from './pregunta-respuesta.service';

@Component({
  selector: 'jhi-pregunta-respuesta',
  templateUrl: './pregunta-respuesta.component.html'
})
export class PreguntaRespuestaComponent implements OnInit, OnDestroy {
  preguntaRespuestas: IPreguntaRespuesta[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected preguntaRespuestaService: PreguntaRespuestaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.preguntaRespuestaService
      .query()
      .pipe(
        filter((res: HttpResponse<IPreguntaRespuesta[]>) => res.ok),
        map((res: HttpResponse<IPreguntaRespuesta[]>) => res.body)
      )
      .subscribe(
        (res: IPreguntaRespuesta[]) => {
          this.preguntaRespuestas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPreguntaRespuestas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPreguntaRespuesta) {
    return item.id;
  }

  registerChangeInPreguntaRespuestas() {
    this.eventSubscriber = this.eventManager.subscribe('preguntaRespuestaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
