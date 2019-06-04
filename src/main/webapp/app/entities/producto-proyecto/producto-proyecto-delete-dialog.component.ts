import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductoProyecto } from 'app/shared/model/producto-proyecto.model';
import { ProductoProyectoService } from './producto-proyecto.service';

@Component({
  selector: 'jhi-producto-proyecto-delete-dialog',
  templateUrl: './producto-proyecto-delete-dialog.component.html'
})
export class ProductoProyectoDeleteDialogComponent {
  productoProyecto: IProductoProyecto;

  constructor(
    protected productoProyectoService: ProductoProyectoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productoProyectoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productoProyectoListModification',
        content: 'Deleted an productoProyecto'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-producto-proyecto-delete-popup',
  template: ''
})
export class ProductoProyectoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productoProyecto }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductoProyectoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.productoProyecto = productoProyecto;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/producto-proyecto', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/producto-proyecto', { outlets: { popup: null } }]);
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
