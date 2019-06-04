import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPresupuestoValor } from 'app/shared/model/presupuesto-valor.model';
import { PresupuestoValorService } from './presupuesto-valor.service';

@Component({
  selector: 'jhi-presupuesto-valor-delete-dialog',
  templateUrl: './presupuesto-valor-delete-dialog.component.html'
})
export class PresupuestoValorDeleteDialogComponent {
  presupuestoValor: IPresupuestoValor;

  constructor(
    protected presupuestoValorService: PresupuestoValorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.presupuestoValorService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'presupuestoValorListModification',
        content: 'Deleted an presupuestoValor'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-presupuesto-valor-delete-popup',
  template: ''
})
export class PresupuestoValorDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ presupuestoValor }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PresupuestoValorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.presupuestoValor = presupuestoValor;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/presupuesto-valor', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/presupuesto-valor', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
