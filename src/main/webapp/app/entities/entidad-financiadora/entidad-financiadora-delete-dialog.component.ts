import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEntidadFinanciadora } from 'app/shared/model/entidad-financiadora.model';
import { EntidadFinanciadoraService } from './entidad-financiadora.service';

@Component({
  selector: 'jhi-entidad-financiadora-delete-dialog',
  templateUrl: './entidad-financiadora-delete-dialog.component.html'
})
export class EntidadFinanciadoraDeleteDialogComponent {
  entidadFinanciadora: IEntidadFinanciadora;

  constructor(
    protected entidadFinanciadoraService: EntidadFinanciadoraService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.entidadFinanciadoraService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'entidadFinanciadoraListModification',
        content: 'Deleted an entidadFinanciadora'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-entidad-financiadora-delete-popup',
  template: ''
})
export class EntidadFinanciadoraDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ entidadFinanciadora }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EntidadFinanciadoraDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.entidadFinanciadora = entidadFinanciadora;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/entidad-financiadora', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/entidad-financiadora', { outlets: { popup: null } }]);
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
