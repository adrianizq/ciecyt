import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from './producto.service';

@Component({
  selector: 'jhi-producto-delete-dialog',
  templateUrl: './producto-delete-dialog.component.html'
})
export class ProductoDeleteDialogComponent {
  producto: IProducto;

  constructor(protected productoService: ProductoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productoListModification',
        content: 'Deleted an producto'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-producto-delete-popup',
  template: ''
})
export class ProductoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ producto }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.producto = producto;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/producto', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/producto', { outlets: { popup: null } }]);
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
