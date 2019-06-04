import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IElementos } from 'app/shared/model/elementos.model';
import { ElementosService } from './elementos.service';

@Component({
  selector: 'jhi-elementos-delete-dialog',
  templateUrl: './elementos-delete-dialog.component.html'
})
export class ElementosDeleteDialogComponent {
  elementos: IElementos;

  constructor(protected elementosService: ElementosService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.elementosService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'elementosListModification',
        content: 'Deleted an elementos'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-elementos-delete-popup',
  template: ''
})
export class ElementosDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ elementos }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ElementosDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.elementos = elementos;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/elementos', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/elementos', { outlets: { popup: null } }]);
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
