import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFacultad } from 'app/shared/model/facultad.model';
import { FacultadService } from './facultad.service';

@Component({
  selector: 'jhi-facultad-delete-dialog',
  templateUrl: './facultad-delete-dialog.component.html'
})
export class FacultadDeleteDialogComponent {
  facultad: IFacultad;

  constructor(protected facultadService: FacultadService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.facultadService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'facultadListModification',
        content: 'Deleted an facultad'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-facultad-delete-popup',
  template: ''
})
export class FacultadDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ facultad }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FacultadDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.facultad = facultad;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/facultad', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/facultad', { outlets: { popup: null } }]);
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
