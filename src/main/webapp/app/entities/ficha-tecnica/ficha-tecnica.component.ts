import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFichaTecnica } from 'app/shared/model/ficha-tecnica.model';
import { AccountService } from 'app/core';
import { FichaTecnicaService } from './ficha-tecnica.service';

@Component({
  selector: 'jhi-ficha-tecnica',
  templateUrl: './ficha-tecnica.component.html'
})
export class FichaTecnicaComponent implements OnInit, OnDestroy {
  fichaTecnicas: IFichaTecnica[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected fichaTecnicaService: FichaTecnicaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.fichaTecnicaService
      .query()
      .pipe(
        filter((res: HttpResponse<IFichaTecnica[]>) => res.ok),
        map((res: HttpResponse<IFichaTecnica[]>) => res.body)
      )
      .subscribe(
        (res: IFichaTecnica[]) => {
          this.fichaTecnicas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInFichaTecnicas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFichaTecnica) {
    return item.id;
  }

  registerChangeInFichaTecnicas() {
    this.eventSubscriber = this.eventManager.subscribe('fichaTecnicaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
