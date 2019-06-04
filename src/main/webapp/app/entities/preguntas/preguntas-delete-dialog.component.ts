import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPreguntas } from 'app/shared/model/preguntas.model';
import { PreguntasService } from './preguntas.service';

@Component({
  selector: 'jhi-preguntas-delete-dialog',
  templateUrl: './preguntas-delete-dialog.component.html'
})
export class PreguntasDeleteDialogComponent {
  preguntas: IPreguntas;

  constructor(protected preguntasService: PreguntasService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.preguntasService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'preguntasListModification',
        content: 'Deleted an preguntas'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-preguntas-delete-popup',
  template: ''
})
export class PreguntasDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ preguntas }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PreguntasDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.preguntas = preguntas;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/preguntas', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/preguntas', { outlets: { popup: null } }]);
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
