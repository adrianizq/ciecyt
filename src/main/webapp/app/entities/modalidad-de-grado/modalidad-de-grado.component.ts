import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IModalidadDeGrado } from 'app/shared/model/modalidad-de-grado.model';
import { AccountService } from 'app/core';
import { ModalidadDeGradoService } from './modalidad-de-grado.service';

@Component({
  selector: 'jhi-modalidad-de-grado',
  templateUrl: './modalidad-de-grado.component.html'
})
export class ModalidadDeGradoComponent implements OnInit, OnDestroy {
  modalidadDeGrados: IModalidadDeGrado[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected modalidadDeGradoService: ModalidadDeGradoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.modalidadDeGradoService
      .query()
      .pipe(
        filter((res: HttpResponse<IModalidadDeGrado[]>) => res.ok),
        map((res: HttpResponse<IModalidadDeGrado[]>) => res.body)
      )
      .subscribe(
        (res: IModalidadDeGrado[]) => {
          this.modalidadDeGrados = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInModalidadDeGrados();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IModalidadDeGrado) {
    return item.id;
  }

  registerChangeInModalidadDeGrados() {
    this.eventSubscriber = this.eventManager.subscribe('modalidadDeGradoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
