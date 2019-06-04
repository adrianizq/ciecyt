import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRolesUsuarioProyecto } from 'app/shared/model/roles-usuario-proyecto.model';
import { RolesUsuarioProyectoService } from './roles-usuario-proyecto.service';

@Component({
  selector: 'jhi-roles-usuario-proyecto-delete-dialog',
  templateUrl: './roles-usuario-proyecto-delete-dialog.component.html'
})
export class RolesUsuarioProyectoDeleteDialogComponent {
  rolesUsuarioProyecto: IRolesUsuarioProyecto;

  constructor(
    protected rolesUsuarioProyectoService: RolesUsuarioProyectoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.rolesUsuarioProyectoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'rolesUsuarioProyectoListModification',
        content: 'Deleted an rolesUsuarioProyecto'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-roles-usuario-proyecto-delete-popup',
  template: ''
})
export class RolesUsuarioProyectoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ rolesUsuarioProyecto }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RolesUsuarioProyectoDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.rolesUsuarioProyecto = rolesUsuarioProyecto;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/roles-usuario-proyecto', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/roles-usuario-proyecto', { outlets: { popup: null } }]);
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
