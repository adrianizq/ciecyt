import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFacultad } from 'app/shared/model/facultad.model';
import { AccountService } from 'app/core';
import { FacultadService } from './facultad.service';

@Component({
  selector: 'jhi-facultad',
  templateUrl: './facultad.component.html'
})
export class FacultadComponent implements OnInit, OnDestroy {
  facultads: IFacultad[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected facultadService: FacultadService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.facultadService
      .query()
      .pipe(
        filter((res: HttpResponse<IFacultad[]>) => res.ok),
        map((res: HttpResponse<IFacultad[]>) => res.body)
      )
      .subscribe(
        (res: IFacultad[]) => {
          this.facultads = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInFacultads();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFacultad) {
    return item.id;
  }

  registerChangeInFacultads() {
    this.eventSubscriber = this.eventManager.subscribe('facultadListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
