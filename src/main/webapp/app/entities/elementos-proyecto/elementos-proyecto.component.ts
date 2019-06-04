import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IElementosProyecto } from 'app/shared/model/elementos-proyecto.model';
import { AccountService } from 'app/core';
import { ElementosProyectoService } from './elementos-proyecto.service';

@Component({
  selector: 'jhi-elementos-proyecto',
  templateUrl: './elementos-proyecto.component.html'
})
export class ElementosProyectoComponent implements OnInit, OnDestroy {
  elementosProyectos: IElementosProyecto[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected elementosProyectoService: ElementosProyectoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.elementosProyectoService
      .query()
      .pipe(
        filter((res: HttpResponse<IElementosProyecto[]>) => res.ok),
        map((res: HttpResponse<IElementosProyecto[]>) => res.body)
      )
      .subscribe(
        (res: IElementosProyecto[]) => {
          this.elementosProyectos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInElementosProyectos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IElementosProyecto) {
    return item.id;
  }

  registerChangeInElementosProyectos() {
    this.eventSubscriber = this.eventManager.subscribe('elementosProyectoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
