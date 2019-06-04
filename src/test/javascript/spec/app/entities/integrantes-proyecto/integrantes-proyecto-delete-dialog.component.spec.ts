/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CiecytTestModule } from '../../../test.module';
import { IntegrantesProyectoDeleteDialogComponent } from 'app/entities/integrantes-proyecto/integrantes-proyecto-delete-dialog.component';
import { IntegrantesProyectoService } from 'app/entities/integrantes-proyecto/integrantes-proyecto.service';

describe('Component Tests', () => {
  describe('IntegrantesProyecto Management Delete Component', () => {
    let comp: IntegrantesProyectoDeleteDialogComponent;
    let fixture: ComponentFixture<IntegrantesProyectoDeleteDialogComponent>;
    let service: IntegrantesProyectoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [IntegrantesProyectoDeleteDialogComponent]
      })
        .overrideTemplate(IntegrantesProyectoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IntegrantesProyectoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IntegrantesProyectoService);
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
