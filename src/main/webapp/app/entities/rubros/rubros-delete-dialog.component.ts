import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRubros } from 'app/shared/model/rubros.model';
import { RubrosService } from './rubros.service';

@Component({
  selector: 'jhi-rubros-delete-dialog',
  templateUrl: './rubros-delete-dialog.component.html'
})
export class RubrosDeleteDialogComponent {
  rubros: IRubros;

  constructor(protected rubrosService: RubrosService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.rubrosService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'rubrosListModification',
        content: 'Deleted an rubros'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-rubros-delete-popup',
  template: ''
})
export class RubrosDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ rubros }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RubrosDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.rubros = rubros;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/rubros', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/rubros', { outlets: { popup: null } }]);
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
