/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CiecytTestModule } from '../../../test.module';
import { RolesUsuarioProyectoDeleteDialogComponent } from 'app/entities/roles-usuario-proyecto/roles-usuario-proyecto-delete-dialog.component';
import { RolesUsuarioProyectoService } from 'app/entities/roles-usuario-proyecto/roles-usuario-proyecto.service';

describe('Component Tests', () => {
  describe('RolesUsuarioProyecto Management Delete Component', () => {
    let comp: RolesUsuarioProyectoDeleteDialogComponent;
    let fixture: ComponentFixture<RolesUsuarioProyectoDeleteDialogComponent>;
    let service: RolesUsuarioProyectoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [RolesUsuarioProyectoDeleteDialogComponent]
      })
        .overrideTemplate(RolesUsuarioProyectoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RolesUsuarioProyectoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RolesUsuarioProyectoService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
