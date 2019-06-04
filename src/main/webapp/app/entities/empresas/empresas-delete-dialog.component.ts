import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmpresas } from 'app/shared/model/empresas.model';
import { EmpresasService } from './empresas.service';

@Component({
  selector: 'jhi-empresas-delete-dialog',
  templateUrl: './empresas-delete-dialog.component.html'
})
export class EmpresasDeleteDialogComponent {
  empresas: IEmpresas;

  constructor(protected empresasService: EmpresasService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.empresasService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'empresasListModification',
        content: 'Deleted an empresas'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-empresas-delete-popup',
  template: ''
})
export class EmpresasDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ empresas }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EmpresasDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.empresas = empresas;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/empresas', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/empresas', { outlets: { popup: null } }]);
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
