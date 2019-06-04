/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CiecytTestModule } from '../../../test.module';
import { FaseTrabajoDeleteDialogComponent } from 'app/entities/fase-trabajo/fase-trabajo-delete-dialog.component';
import { FaseTrabajoService } from 'app/entities/fase-trabajo/fase-trabajo.service';

describe('Component Tests', () => {
  describe('FaseTrabajo Management Delete Component', () => {
    let comp: FaseTrabajoDeleteDialogComponent;
    let fixture: ComponentFixture<FaseTrabajoDeleteDialogComponent>;
    let service: FaseTrabajoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [FaseTrabajoDeleteDialogComponent]
      })
        .overrideTemplate(FaseTrabajoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FaseTrabajoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FaseTrabajoService);
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
