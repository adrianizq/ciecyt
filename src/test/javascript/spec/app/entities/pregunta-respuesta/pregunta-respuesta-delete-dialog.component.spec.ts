/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CiecytTestModule } from '../../../test.module';
import { PreguntaRespuestaDeleteDialogComponent } from 'app/entities/pregunta-respuesta/pregunta-respuesta-delete-dialog.component';
import { PreguntaRespuestaService } from 'app/entities/pregunta-respuesta/pregunta-respuesta.service';

describe('Component Tests', () => {
  describe('PreguntaRespuesta Management Delete Component', () => {
    let comp: PreguntaRespuestaDeleteDialogComponent;
    let fixture: ComponentFixture<PreguntaRespuestaDeleteDialogComponent>;
    let service: PreguntaRespuestaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [PreguntaRespuestaDeleteDialogComponent]
      })
        .overrideTemplate(PreguntaRespuestaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PreguntaRespuestaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PreguntaRespuestaService);
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
