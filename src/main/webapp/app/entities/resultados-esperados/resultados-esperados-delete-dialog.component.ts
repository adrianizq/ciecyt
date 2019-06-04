import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResultadosEsperados } from 'app/shared/model/resultados-esperados.model';
import { ResultadosEsperadosService } from './resultados-esperados.service';

@Component({
  selector: 'jhi-resultados-esperados-delete-dialog',
  templateUrl: './resultados-esperados-delete-dialog.component.html'
})
export class ResultadosEsperadosDeleteDialogComponent {
  resultadosEsperados: IResultadosEsperados;

  constructor(
    protected resultadosEsperadosService: ResultadosEsperadosService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.resultadosEsperadosService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'resultadosEsperadosListModification',
        content: 'Deleted an resultadosEsperados'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-resultados-esperados-delete-popup',
  template: ''
})
export class ResultadosEsperadosDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ resultadosEsperados }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ResultadosEsperadosDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.resultadosEsperados = resultadosEsperados;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/resultados-esperados', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/resultados-esperados', { outlets: { popup: null } }]);
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
