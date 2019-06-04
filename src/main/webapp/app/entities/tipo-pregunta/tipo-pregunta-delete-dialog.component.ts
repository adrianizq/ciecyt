import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoPregunta } from 'app/shared/model/tipo-pregunta.model';
import { TipoPreguntaService } from './tipo-pregunta.service';

@Component({
  selector: 'jhi-tipo-pregunta-delete-dialog',
  templateUrl: './tipo-pregunta-delete-dialog.component.html'
})
export class TipoPreguntaDeleteDialogComponent {
  tipoPregunta: ITipoPregunta;

  constructor(
    protected tipoPreguntaService: TipoPreguntaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tipoPreguntaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tipoPreguntaListModification',
        content: 'Deleted an tipoPregunta'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-tipo-pregunta-delete-popup',
  template: ''
})
export class TipoPreguntaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipoPregunta }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TipoPreguntaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tipoPregunta = tipoPregunta;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/tipo-pregunta', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/tipo-pregunta', { outlets: { popup: null } }]);
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
