import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICategorizacion } from 'app/shared/model/categorizacion.model';
import { CategorizacionService } from './categorizacion.service';

@Component({
  selector: 'jhi-categorizacion-delete-dialog',
  templateUrl: './categorizacion-delete-dialog.component.html'
})
export class CategorizacionDeleteDialogComponent {
  categorizacion: ICategorizacion;

  constructor(
    protected categorizacionService: CategorizacionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.categorizacionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'categorizacionListModification',
        content: 'Deleted an categorizacion'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-categorizacion-delete-popup',
  template: ''
})
export class CategorizacionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ categorizacion }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CategorizacionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.categorizacion = categorizacion;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/categorizacion', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/categorizacion', { outlets: { popup: null } }]);
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
