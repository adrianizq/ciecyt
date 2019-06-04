import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIntegrantesProyecto } from 'app/shared/model/integrantes-proyecto.model';
import { IntegrantesProyectoService } from './integrantes-proyecto.service';

@Component({
  selector: 'jhi-integrantes-proyecto-delete-dialog',
  templateUrl: './integrantes-proyecto-delete-dialog.component.html'
})
export class IntegrantesProyectoDeleteDialogComponent {
  integrantesProyecto: IIntegrantesProyecto;

  constructor(
    protected integrantesProyectoService: IntegrantesProyectoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.integrantesProyectoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'integrantesProyectoListModification',
        content: 'Deleted an integrantesProyecto'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-integrantes-proyecto-delete-popup',
  template: ''
})
export class IntegrantesProyectoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ integrantesProyecto }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(IntegrantesProyectoDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.integrantesProyecto = integrantesProyecto;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/integrantes-proyecto', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/integrantes-proyecto', { outlets: { popup: null } }]);
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
