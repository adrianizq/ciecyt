import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPreguntas } from 'app/shared/model/preguntas.model';
import { AccountService } from 'app/core';
import { PreguntasService } from './preguntas.service';

@Component({
  selector: 'jhi-preguntas',
  templateUrl: './preguntas.component.html'
})
export class PreguntasComponent implements OnInit, OnDestroy {
  preguntas: IPreguntas[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected preguntasService: PreguntasService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.preguntasService
      .query()
      .pipe(
        filter((res: HttpResponse<IPreguntas[]>) => res.ok),
        map((res: HttpResponse<IPreguntas[]>) => res.body)
      )
      .subscribe(
        (res: IPreguntas[]) => {
          this.preguntas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPreguntas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPreguntas) {
    return item.id;
  }

  registerChangeInPreguntas() {
    this.eventSubscriber = this.eventManager.subscribe('preguntasListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
