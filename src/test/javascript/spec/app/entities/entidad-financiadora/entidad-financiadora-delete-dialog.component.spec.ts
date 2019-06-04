/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CiecytTestModule } from '../../../test.module';
import { EntidadFinanciadoraDeleteDialogComponent } from 'app/entities/entidad-financiadora/entidad-financiadora-delete-dialog.component';
import { EntidadFinanciadoraService } from 'app/entities/entidad-financiadora/entidad-financiadora.service';

describe('Component Tests', () => {
  describe('EntidadFinanciadora Management Delete Component', () => {
    let comp: EntidadFinanciadoraDeleteDialogComponent;
    let fixture: ComponentFixture<EntidadFinanciadoraDeleteDialogComponent>;
    let service: EntidadFinanciadoraService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [EntidadFinanciadoraDeleteDialogComponent]
      })
        .overrideTemplate(EntidadFinanciadoraDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EntidadFinanciadoraDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EntidadFinanciadoraService);
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
