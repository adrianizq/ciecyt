import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGrupoSemillero } from 'app/shared/model/grupo-semillero.model';
import { GrupoSemilleroService } from './grupo-semillero.service';

@Component({
  selector: 'jhi-grupo-semillero-delete-dialog',
  templateUrl: './grupo-semillero-delete-dialog.component.html'
})
export class GrupoSemilleroDeleteDialogComponent {
  grupoSemillero: IGrupoSemillero;

  constructor(
    protected grupoSemilleroService: GrupoSemilleroService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.grupoSemilleroService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'grupoSemilleroListModification',
        content: 'Deleted an grupoSemillero'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-grupo-semillero-delete-popup',
  template: ''
})
export class GrupoSemilleroDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ grupoSemillero }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(GrupoSemilleroDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.grupoSemillero = grupoSemillero;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/grupo-semillero', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/grupo-semillero', { outlets: { popup: null } }]);
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
