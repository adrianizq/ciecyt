import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IElementos } from 'app/shared/model/elementos.model';
import { AccountService } from 'app/core';
import { ElementosService } from './elementos.service';

@Component({
  selector: 'jhi-elementos',
  templateUrl: './elementos.component.html'
})
export class ElementosComponent implements OnInit, OnDestroy {
  elementos: IElementos[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected elementosService: ElementosService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.elementosService
      .query()
      .pipe(
        filter((res: HttpResponse<IElementos[]>) => res.ok),
        map((res: HttpResponse<IElementos[]>) => res.body)
      )
      .subscribe(
        (res: IElementos[]) => {
          this.elementos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInElementos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IElementos) {
    return item.id;
  }

  registerChangeInElementos() {
    this.eventSubscriber = this.eventManager.subscribe('elementosListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
