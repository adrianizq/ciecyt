import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICronograma } from 'app/shared/model/cronograma.model';
import { CronogramaService } from './cronograma.service';

@Component({
  selector: 'jhi-cronograma-delete-dialog',
  templateUrl: './cronograma-delete-dialog.component.html'
})
export class CronogramaDeleteDialogComponent {
  cronograma: ICronograma;

  constructor(
    protected cronogramaService: CronogramaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.cronogramaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'cronogramaListModification',
        content: 'Deleted an cronograma'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-cronograma-delete-popup',
  template: ''
})
export class CronogramaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cronograma }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CronogramaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.cronograma = cronograma;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/cronograma', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/cronograma', { outlets: { popup: null } }]);
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
