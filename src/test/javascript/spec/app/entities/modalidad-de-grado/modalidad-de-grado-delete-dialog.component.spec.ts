/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CiecytTestModule } from '../../../test.module';
import { ModalidadDeGradoDeleteDialogComponent } from 'app/entities/modalidad-de-grado/modalidad-de-grado-delete-dialog.component';
import { ModalidadDeGradoService } from 'app/entities/modalidad-de-grado/modalidad-de-grado.service';

describe('Component Tests', () => {
  describe('ModalidadDeGrado Management Delete Component', () => {
    let comp: ModalidadDeGradoDeleteDialogComponent;
    let fixture: ComponentFixture<ModalidadDeGradoDeleteDialogComponent>;
    let service: ModalidadDeGradoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [ModalidadDeGradoDeleteDialogComponent]
      })
        .overrideTemplate(ModalidadDeGradoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ModalidadDeGradoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ModalidadDeGradoService);
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
