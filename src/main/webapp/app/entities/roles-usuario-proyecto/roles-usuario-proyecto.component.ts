import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRolesUsuarioProyecto } from 'app/shared/model/roles-usuario-proyecto.model';
import { AccountService } from 'app/core';
import { RolesUsuarioProyectoService } from './roles-usuario-proyecto.service';

@Component({
  selector: 'jhi-roles-usuario-proyecto',
  templateUrl: './roles-usuario-proyecto.component.html'
})
export class RolesUsuarioProyectoComponent implements OnInit, OnDestroy {
  rolesUsuarioProyectos: IRolesUsuarioProyecto[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected rolesUsuarioProyectoService: RolesUsuarioProyectoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.rolesUsuarioProyectoService
      .query()
      .pipe(
        filter((res: HttpResponse<IRolesUsuarioProyecto[]>) => res.ok),
        map((res: HttpResponse<IRolesUsuarioProyecto[]>) => res.body)
      )
      .subscribe(
        (res: IRolesUsuarioProyecto[]) => {
          this.rolesUsuarioProyectos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInRolesUsuarioProyectos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRolesUsuarioProyecto) {
    return item.id;
  }

  registerChangeInRolesUsuarioProyectos() {
    this.eventSubscriber = this.eventManager.subscribe('rolesUsuarioProyectoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
