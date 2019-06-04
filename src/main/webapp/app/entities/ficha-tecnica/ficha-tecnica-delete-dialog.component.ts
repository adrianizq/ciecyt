import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFichaTecnica } from 'app/shared/model/ficha-tecnica.model';
import { FichaTecnicaService } from './ficha-tecnica.service';

@Component({
  selector: 'jhi-ficha-tecnica-delete-dialog',
  templateUrl: './ficha-tecnica-delete-dialog.component.html'
})
export class FichaTecnicaDeleteDialogComponent {
  fichaTecnica: IFichaTecnica;

  constructor(
    protected fichaTecnicaService: FichaTecnicaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.fichaTecnicaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'fichaTecnicaListModification',
        content: 'Deleted an fichaTecnica'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-ficha-tecnica-delete-popup',
  template: ''
})
export class FichaTecnicaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fichaTecnica }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FichaTecnicaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.fichaTecnica = fichaTecnica;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/ficha-tecnica', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/ficha-tecnica', { outlets: { popup: null } }]);
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
