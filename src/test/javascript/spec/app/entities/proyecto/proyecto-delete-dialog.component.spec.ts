/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CiecytTestModule } from '../../../test.module';
import { ProyectoDeleteDialogComponent } from 'app/entities/proyecto/proyecto-delete-dialog.component';
import { ProyectoService } from 'app/entities/proyecto/proyecto.service';

describe('Component Tests', () => {
  describe('Proyecto Management Delete Component', () => {
    let comp: ProyectoDeleteDialogComponent;
    let fixture: ComponentFixture<ProyectoDeleteDialogComponent>;
    let service: ProyectoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [ProyectoDeleteDialogComponent]
      })
        .overrideTemplate(ProyectoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProyectoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProyectoService);
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
