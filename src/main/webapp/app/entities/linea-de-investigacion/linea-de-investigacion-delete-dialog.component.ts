import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILineaDeInvestigacion } from 'app/shared/model/linea-de-investigacion.model';
import { LineaDeInvestigacionService } from './linea-de-investigacion.service';

@Component({
  selector: 'jhi-linea-de-investigacion-delete-dialog',
  templateUrl: './linea-de-investigacion-delete-dialog.component.html'
})
export class LineaDeInvestigacionDeleteDialogComponent {
  lineaDeInvestigacion: ILineaDeInvestigacion;

  constructor(
    protected lineaDeInvestigacionService: LineaDeInvestigacionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.lineaDeInvestigacionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'lineaDeInvestigacionListModification',
        content: 'Deleted an lineaDeInvestigacion'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-linea-de-investigacion-delete-popup',
  template: ''
})
export class LineaDeInvestigacionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ lineaDeInvestigacion }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LineaDeInvestigacionDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.lineaDeInvestigacion = lineaDeInvestigacion;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/linea-de-investigacion', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/linea-de-investigacion', { outlets: { popup: null } }]);
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
