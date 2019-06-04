import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IElementosProyecto } from 'app/shared/model/elementos-proyecto.model';
import { ElementosProyectoService } from './elementos-proyecto.service';

@Component({
  selector: 'jhi-elementos-proyecto-delete-dialog',
  templateUrl: './elementos-proyecto-delete-dialog.component.html'
})
export class ElementosProyectoDeleteDialogComponent {
  elementosProyecto: IElementosProyecto;

  constructor(
    protected elementosProyectoService: ElementosProyectoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.elementosProyectoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'elementosProyectoListModification',
        content: 'Deleted an elementosProyecto'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-elementos-proyecto-delete-popup',
  template: ''
})
export class ElementosProyectoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ elementosProyecto }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ElementosProyectoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.elementosProyecto = elementosProyecto;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/elementos-proyecto', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/elementos-proyecto', { outlets: { popup: null } }]);
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
