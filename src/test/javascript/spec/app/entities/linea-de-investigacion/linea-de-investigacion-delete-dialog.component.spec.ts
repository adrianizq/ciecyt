/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CiecytTestModule } from '../../../test.module';
import { LineaDeInvestigacionDeleteDialogComponent } from 'app/entities/linea-de-investigacion/linea-de-investigacion-delete-dialog.component';
import { LineaDeInvestigacionService } from 'app/entities/linea-de-investigacion/linea-de-investigacion.service';

describe('Component Tests', () => {
  describe('LineaDeInvestigacion Management Delete Component', () => {
    let comp: LineaDeInvestigacionDeleteDialogComponent;
    let fixture: ComponentFixture<LineaDeInvestigacionDeleteDialogComponent>;
    let service: LineaDeInvestigacionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [LineaDeInvestigacionDeleteDialogComponent]
      })
        .overrideTemplate(LineaDeInvestigacionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LineaDeInvestigacionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LineaDeInvestigacionService);
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
