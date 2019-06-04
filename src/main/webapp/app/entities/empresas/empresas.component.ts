import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEmpresas } from 'app/shared/model/empresas.model';
import { AccountService } from 'app/core';
import { EmpresasService } from './empresas.service';

@Component({
  selector: 'jhi-empresas',
  templateUrl: './empresas.component.html'
})
export class EmpresasComponent implements OnInit, OnDestroy {
  empresas: IEmpresas[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected empresasService: EmpresasService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.empresasService
      .query()
      .pipe(
        filter((res: HttpResponse<IEmpresas[]>) => res.ok),
        map((res: HttpResponse<IEmpresas[]>) => res.body)
      )
      .subscribe(
        (res: IEmpresas[]) => {
          this.empresas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInEmpresas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEmpresas) {
    return item.id;
  }

  registerChangeInEmpresas() {
    this.eventSubscriber = this.eventManager.subscribe('empresasListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
