import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFaseTrabajo } from 'app/shared/model/fase-trabajo.model';
import { FaseTrabajoService } from './fase-trabajo.service';

@Component({
  selector: 'jhi-fase-trabajo-delete-dialog',
  templateUrl: './fase-trabajo-delete-dialog.component.html'
})
export class FaseTrabajoDeleteDialogComponent {
  faseTrabajo: IFaseTrabajo;

  constructor(
    protected faseTrabajoService: FaseTrabajoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.faseTrabajoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'faseTrabajoListModification',
        content: 'Deleted an faseTrabajo'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-fase-trabajo-delete-popup',
  template: ''
})
export class FaseTrabajoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ faseTrabajo }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FaseTrabajoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.faseTrabajo = faseTrabajo;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/fase-trabajo', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/fase-trabajo', { outlets: { popup: null } }]);
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
