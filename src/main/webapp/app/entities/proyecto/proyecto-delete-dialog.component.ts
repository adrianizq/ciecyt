import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProyecto } from 'app/shared/model/proyecto.model';
import { ProyectoService } from './proyecto.service';

@Component({
  selector: 'jhi-proyecto-delete-dialog',
  templateUrl: './proyecto-delete-dialog.component.html'
})
export class ProyectoDeleteDialogComponent {
  proyecto: IProyecto;

  constructor(protected proyectoService: ProyectoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.proyectoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'proyectoListModification',
        content: 'Deleted an proyecto'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-proyecto-delete-popup',
  template: ''
})
export class ProyectoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ proyecto }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProyectoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.proyecto = proyecto;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/proyecto', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/proyecto', { outlets: { popup: null } }]);
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
