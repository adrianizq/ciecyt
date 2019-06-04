import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISolicitud } from 'app/shared/model/solicitud.model';
import { AccountService } from 'app/core';
import { SolicitudService } from './solicitud.service';

@Component({
  selector: 'jhi-solicitud',
  templateUrl: './solicitud.component.html'
})
export class SolicitudComponent implements OnInit, OnDestroy {
  solicituds: ISolicitud[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected solicitudService: SolicitudService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.solicitudService
      .query()
      .pipe(
        filter((res: HttpResponse<ISolicitud[]>) => res.ok),
        map((res: HttpResponse<ISolicitud[]>) => res.body)
      )
      .subscribe(
        (res: ISolicitud[]) => {
          this.solicituds = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSolicituds();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISolicitud) {
    return item.id;
  }

  registerChangeInSolicituds() {
    this.eventSubscriber = this.eventManager.subscribe('solicitudListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
