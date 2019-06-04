import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISolicitud } from 'app/shared/model/solicitud.model';
import { SolicitudService } from './solicitud.service';

@Component({
  selector: 'jhi-solicitud-delete-dialog',
  templateUrl: './solicitud-delete-dialog.component.html'
})
export class SolicitudDeleteDialogComponent {
  solicitud: ISolicitud;

  constructor(protected solicitudService: SolicitudService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.solicitudService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'solicitudListModification',
        content: 'Deleted an solicitud'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-solicitud-delete-popup',
  template: ''
})
export class SolicitudDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ solicitud }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SolicitudDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.solicitud = solicitud;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/solicitud', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/solicitud', { outlets: { popup: null } }]);
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
