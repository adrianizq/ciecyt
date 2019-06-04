/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CiecytTestModule } from '../../../test.module';
import { CategorizacionDeleteDialogComponent } from 'app/entities/categorizacion/categorizacion-delete-dialog.component';
import { CategorizacionService } from 'app/entities/categorizacion/categorizacion.service';

describe('Component Tests', () => {
  describe('Categorizacion Management Delete Component', () => {
    let comp: CategorizacionDeleteDialogComponent;
    let fixture: ComponentFixture<CategorizacionDeleteDialogComponent>;
    let service: CategorizacionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [CategorizacionDeleteDialogComponent]
      })
        .overrideTemplate(CategorizacionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategorizacionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategorizacionService);
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
