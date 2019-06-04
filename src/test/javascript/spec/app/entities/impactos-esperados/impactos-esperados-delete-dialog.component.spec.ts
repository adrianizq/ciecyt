/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CiecytTestModule } from '../../../test.module';
import { ImpactosEsperadosDeleteDialogComponent } from 'app/entities/impactos-esperados/impactos-esperados-delete-dialog.component';
import { ImpactosEsperadosService } from 'app/entities/impactos-esperados/impactos-esperados.service';

describe('Component Tests', () => {
  describe('ImpactosEsperados Management Delete Component', () => {
    let comp: ImpactosEsperadosDeleteDialogComponent;
    let fixture: ComponentFixture<ImpactosEsperadosDeleteDialogComponent>;
    let service: ImpactosEsperadosService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [ImpactosEsperadosDeleteDialogComponent]
      })
        .overrideTemplate(ImpactosEsperadosDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ImpactosEsperadosDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImpactosEsperadosService);
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
