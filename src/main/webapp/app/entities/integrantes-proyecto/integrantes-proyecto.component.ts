import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IIntegrantesProyecto } from 'app/shared/model/integrantes-proyecto.model';
import { AccountService } from 'app/core';
import { IntegrantesProyectoService } from './integrantes-proyecto.service';

@Component({
  selector: 'jhi-integrantes-proyecto',
  templateUrl: './integrantes-proyecto.component.html'
})
export class IntegrantesProyectoComponent implements OnInit, OnDestroy {
  integrantesProyectos: IIntegrantesProyecto[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected integrantesProyectoService: IntegrantesProyectoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.integrantesProyectoService
      .query()
      .pipe(
        filter((res: HttpResponse<IIntegrantesProyecto[]>) => res.ok),
        map((res: HttpResponse<IIntegrantesProyecto[]>) => res.body)
      )
      .subscribe(
        (res: IIntegrantesProyecto[]) => {
          this.integrantesProyectos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInIntegrantesProyectos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IIntegrantesProyecto) {
    return item.id;
  }

  registerChangeInIntegrantesProyectos() {
    this.eventSubscriber = this.eventManager.subscribe('integrantesProyectoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
