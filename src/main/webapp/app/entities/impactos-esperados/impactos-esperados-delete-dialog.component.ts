import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IImpactosEsperados } from 'app/shared/model/impactos-esperados.model';
import { ImpactosEsperadosService } from './impactos-esperados.service';

@Component({
  selector: 'jhi-impactos-esperados-delete-dialog',
  templateUrl: './impactos-esperados-delete-dialog.component.html'
})
export class ImpactosEsperadosDeleteDialogComponent {
  impactosEsperados: IImpactosEsperados;

  constructor(
    protected impactosEsperadosService: ImpactosEsperadosService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.impactosEsperadosService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'impactosEsperadosListModification',
        content: 'Deleted an impactosEsperados'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-impactos-esperados-delete-popup',
  template: ''
})
export class ImpactosEsperadosDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ impactosEsperados }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ImpactosEsperadosDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.impactosEsperados = impactosEsperados;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/impactos-esperados', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/impactos-esperados', { outlets: { popup: null } }]);
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
