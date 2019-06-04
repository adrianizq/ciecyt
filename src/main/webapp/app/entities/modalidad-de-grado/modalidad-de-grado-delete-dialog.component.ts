import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IModalidadDeGrado } from 'app/shared/model/modalidad-de-grado.model';
import { ModalidadDeGradoService } from './modalidad-de-grado.service';

@Component({
  selector: 'jhi-modalidad-de-grado-delete-dialog',
  templateUrl: './modalidad-de-grado-delete-dialog.component.html'
})
export class ModalidadDeGradoDeleteDialogComponent {
  modalidadDeGrado: IModalidadDeGrado;

  constructor(
    protected modalidadDeGradoService: ModalidadDeGradoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.modalidadDeGradoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'modalidadDeGradoListModification',
        content: 'Deleted an modalidadDeGrado'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-modalidad-de-grado-delete-popup',
  template: ''
})
export class ModalidadDeGradoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ modalidadDeGrado }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ModalidadDeGradoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.modalidadDeGrado = modalidadDeGrado;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/modalidad-de-grado', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/modalidad-de-grado', { outlets: { popup: null } }]);
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
