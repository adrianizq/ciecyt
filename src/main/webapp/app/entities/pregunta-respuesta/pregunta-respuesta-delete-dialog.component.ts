import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPreguntaRespuesta } from 'app/shared/model/pregunta-respuesta.model';
import { PreguntaRespuestaService } from './pregunta-respuesta.service';

@Component({
  selector: 'jhi-pregunta-respuesta-delete-dialog',
  templateUrl: './pregunta-respuesta-delete-dialog.component.html'
})
export class PreguntaRespuestaDeleteDialogComponent {
  preguntaRespuesta: IPreguntaRespuesta;

  constructor(
    protected preguntaRespuestaService: PreguntaRespuestaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.preguntaRespuestaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'preguntaRespuestaListModification',
        content: 'Deleted an preguntaRespuesta'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pregunta-respuesta-delete-popup',
  template: ''
})
export class PreguntaRespuestaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ preguntaRespuesta }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PreguntaRespuestaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.preguntaRespuesta = preguntaRespuesta;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pregunta-respuesta', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pregunta-respuesta', { outlets: { popup: null } }]);
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
